package com.road.rampage.state;

import com.road.rampage.core.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class PauseState implements GameState {

    private final GameState previous;

    public PauseState(GameState previous) {
        this.previous = previous;
    }

    @Override public void enter() {}
    @Override public void exit() {}
    @Override public void update(double dt) {}

    @Override
    public void render(GraphicsContext gc) {
        previous.render(gc);

        gc.setFill(Color.color(0, 0, 0, 0.6));
        gc.fillRect(0, 0, 800, 600);

        gc.setFill(Color.WHITE);
        gc.fillText("PAUSED", 40, 60);
        gc.fillText("ESC: Resume", 40, 95);
        gc.fillText("R: Menu", 40, 125);
    }

    @Override
    public void onKeyPressed(KeyCode code) {
        if (code == KeyCode.ESCAPE) {
            Game.getInstance().setState(previous);
        } else if (code == KeyCode.R) {
            Game.getInstance().setState(new MenuState());
        }
    }

    @Override
    public void onKeyReleased(KeyCode code) {
        // no-op
    }

    @Override
    public String getName() {
        return "PAUSED";
    }
}
