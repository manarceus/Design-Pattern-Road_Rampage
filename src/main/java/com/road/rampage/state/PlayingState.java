package com.road.rampage.state;

import com.road.rampage.core.Game;
import com.road.rampage.core.GameLogger;
import com.road.rampage.player.Player;
import com.road.rampage.util.Rect;
import com.road.rampage.world.Car;
import com.road.rampage.world.Level;
import com.road.rampage.world.LaneIndex;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PlayingState implements GameState {

    private final Set<KeyCode> keys = new HashSet<>();

    private Player player;
    private Level level;

    private int score = 0;
    private double scoreTimer = 0;

    // background image for playing interface
    private Image bgImage = null;

    @Override
    public void enter() {
        keys.clear();
        score = 0;
        scoreTimer = 0;

        level = new Level();
        player = new Player(140, 0);
        player.bindRoad(level.getRoad());

        // Try to load the interface background image (fallback to solid color if missing)
        try {
            File f = new File("C:\\Users\\alaaj\\Desktop\\Education\\2eme_ing\\Design_pattern\\Project\\Road_Rampage\\roadrampage\\Background1.png");
            if (f.exists()) {
                bgImage = new Image(f.toURI().toString());
            }
        } catch (Exception ex) {
            // ignore and fall back to color background
        }
    }

    @Override
    public void exit() {
        keys.clear();
    }

    @Override
    public void update(double dt) {
        player.handleInput(keys);
        player.update(dt);

        level.update(dt);

        // score increases over time
        scoreTimer += dt;
        if (scoreTimer >= 0.25) {
            scoreTimer = 0;
            score += 10;
            Game.getInstance().notifyObservers(com.road.rampage.core.EventType.SCORE_CHANGED, score);
        }

        // collision: check only cars in current lane
        LaneIndex lane = player.getLane();
        Rect p = player.getBounds();

        for (Car car : level.getRoad().getCarsInLane(lane)) {
            if (!car.isAlive()) continue;

            if (p.intersects(car.getBounds())) {
                GameLogger.getInstance().info("Collision: Player hit Car");

                // Use player's takeHit() which handles shield consumption
                boolean dead = player.takeHit();

                // notify observers that player was hit (payload: Player)
                Game.getInstance().notifyObservers(com.road.rampage.core.EventType.PLAYER_HIT, player);

                car.kill();

                // notify observers that a car was destroyed (payload: Car)
                Game.getInstance().notifyObservers(com.road.rampage.core.EventType.CAR_DESTROYED, car);

                if (dead) {
                    Game.getInstance().setState(new GameOverState(score));
                    return;
                } else {
                    score += 50; // reward for destroying car on shield or surviving
                    GameLogger.getInstance().info("Entity destroyed: Car (player survived)");
                    Game.getInstance().notifyObservers(com.road.rampage.core.EventType.SCORE_CHANGED, score);
                }
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        // draw background image if available, otherwise fallback to color
        if (bgImage != null) {
            gc.drawImage(bgImage, 0, 0, 800, 600);
        } else {
            gc.setFill(Color.LIGHTSKYBLUE);
            gc.fillRect(0, 0, 800, 600);
        }

        level.render(gc);
        player.render(gc);

        // HUD styling: modern clean font + subtle shadow and translucent background
        Font hudFont = Font.font("Segoe UI", FontWeight.SEMI_BOLD, 16);
        gc.setFont(hudFont);

        // HUD background box for legibility
        gc.setFill(Color.rgb(0, 0, 0, 0.45));
        gc.fillRoundRect(10, 10, 230, 150, 8, 8);

        // draw each label with a subtle shadow
        double x = 20;
        double y = 35;
        double lineHeight = 25;

        // Score
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillText("Score: " + score, x + 1, y + 1); // shadow
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, x, y);

        // Lane
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillText("Lane: " + player.getLane(), x + 1, y + lineHeight + 1);
        gc.setFill(Color.WHITE);
        gc.fillText("Lane: " + player.getLane(), x, y + lineHeight);

        // Power-ups counts
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillText("SpeedBoost: " + player.getPowerUpManager().getSpeedBoostUses(), x + 1, y + lineHeight * 2 + 1);
        gc.setFill(Color.WHITE);
        gc.fillText("SpeedBoost: " + player.getPowerUpManager().getSpeedBoostUses(), x, y + lineHeight * 2);

        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillText("Shield: " + player.getPowerUpManager().getShieldUses(), x + 1, y + lineHeight * 3 + 1);
        gc.setFill(Color.WHITE);
        gc.fillText("Shield: " + player.getPowerUpManager().getShieldUses(), x, y + lineHeight * 3);

        // Hits remaining (HP)
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillText("HP: " + player.getHitsRemaining(), x + 1, y + lineHeight * 4 + 1);
        gc.setFill(Color.WHITE);
        gc.fillText("HP: " + player.getHitsRemaining(), x, y + lineHeight * 4);

        // Controls hint - smaller and lighter
        Font hintFont = Font.font("Segoe UI", FontWeight.NORMAL, 12);
        gc.setFont(hintFont);
        gc.setFill(Color.rgb(255, 255, 255, 0.9));
        gc.fillText("A/D or ←/→: change lane    SPACE: jump    ESC: pause", 20, 560);

        // restore font for other rendering if needed
        gc.setFont(hudFont);
    }

    @Override
    public void onKeyPressed(KeyCode code) {
        // keep the key set for states that poll it each frame (e.g. jump)
        keys.add(code);

        // handle single-press lane movement immediately to avoid double-processing
        if (code == KeyCode.LEFT || code == KeyCode.A) {
            player.moveLaneLeft();
            return;
        } else if (code == KeyCode.RIGHT || code == KeyCode.D) {
            player.moveLaneRight();
            return;
        }

        if (code == KeyCode.ESCAPE) {
            Game.getInstance().setState(new PauseState(this));
        } else if (code == KeyCode.DIGIT1) {
            player.applySpeedBoost(5.0);  // 5 seconds, limited uses
            Game.getInstance().notifyObservers(com.road.rampage.core.EventType.POWERUP_USED, "SPEEDBOOST");
        } else if (code == KeyCode.DIGIT2) {
            player.applyShield(5.0);      // 5 seconds, limited uses
            Game.getInstance().notifyObservers(com.road.rampage.core.EventType.POWERUP_USED, "SHIELD");
        }
    }

    @Override
    public void onKeyReleased(KeyCode code) {
        keys.remove(code);
    }


    @Override
    public String getName() {
        return "PLAYING";
    }
}
