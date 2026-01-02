package com.road.rampage.state;

import com.road.rampage.core.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;

import java.io.File;

public class GameOverState implements GameState {

    private final int finalScore;

    // cached background image for game over screen (loaded lazily)
    private static Image bgImage = null;
    private static boolean bgTried = false;

    public GameOverState(int finalScore) {
        this.finalScore = finalScore;
    }

    @Override public void enter() {}
    @Override public void exit() {}
    @Override public void update(double dt) {}

    @Override
    public void render(GraphicsContext gc) {
        // lazy-load background image once
        if (!bgTried) {
            bgTried = true;
            try {
                File f = new File("C:\\Users\\alaaj\\Desktop\\Education\\2eme_ing\\Design_pattern\\Project\\Road_Rampage\\roadrampage\\Background1.png");
                if (f.exists()) bgImage = new Image(f.toURI().toString());
            } catch (Exception ex) {
                // ignore, we'll fall back to solid color
            }
        }

        // draw background image if available, otherwise fallback to dark color
        if (bgImage != null) {
            gc.drawImage(bgImage, 0, 0, 800, 600);
        } else {
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 0, 800, 600);
        }

        // translucent panel
        gc.setFill(Color.rgb(255, 255, 255, 0.06));
        gc.fillRoundRect(60, 80, 680, 440, 12, 12);

        // Title
        Font titleFont = Font.font("Segoe UI", FontWeight.BOLD, 48);
        gc.setFont(titleFont);

        String title = "GAME OVER";
        Text titleText = new Text(title);
        titleText.setFont(titleFont);
        double titleX = 400 - titleText.getLayoutBounds().getWidth() / 2.0;
        double titleY = 220;

        // shadow
        gc.setFill(Color.rgb(0, 0, 0, 0.75));
        gc.fillText(title, titleX + 2, titleY + 2);
        // main text
        gc.setFill(Color.WHITE);
        gc.fillText(title, titleX, titleY);

        // Score
        Font scoreFont = Font.font("Segoe UI", FontWeight.SEMI_BOLD, 28);
        gc.setFont(scoreFont);
        String scoreText = "Score: " + finalScore;
        Text scoreTextNode = new Text(scoreText);
        scoreTextNode.setFont(scoreFont);
        double scoreX = 400 - scoreTextNode.getLayoutBounds().getWidth() / 2.0;
        double scoreY = titleY + 60;
        gc.setFill(Color.rgb(255, 255, 255, 0.9));
        gc.fillText(scoreText, scoreX, scoreY);

        // instruction
        Font hintFont = Font.font("Segoe UI", FontWeight.NORMAL, 16);
        gc.setFont(hintFont);
        String hint = "Press ENTER to return to menu";
        Text hintText = new Text(hint);
        hintText.setFont(hintFont);
        double hintX = 400 - hintText.getLayoutBounds().getWidth() / 2.0;
        double hintY = scoreY + 50;
        gc.setFill(Color.rgb(255, 255, 255, 0.8));
        gc.fillText(hint, hintX, hintY);
    }

    @Override
    public void onKeyPressed(KeyCode code) {
        if (code == KeyCode.ENTER) {
            Game.getInstance().setState(new MenuState());
        }
    }

    @Override
    public void onKeyReleased(KeyCode code) {
        // no-op
    }

    @Override
    public String getName() {
        return "GAME_OVER";
    }
}
