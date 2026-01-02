package com.road.rampage.state;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public interface GameState {
    void enter();
    void exit();
    void update(double dt);
    void render(GraphicsContext gc);

    void onKeyPressed(KeyCode code);
    void onKeyReleased(KeyCode code);

    String getName();
}
