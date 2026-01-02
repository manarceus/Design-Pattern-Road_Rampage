package com.road.rampage.character;

import javafx.scene.canvas.GraphicsContext;

public interface Character {
    void update(double dt);
    void render(GraphicsContext gc);

    int getSpeed();        // used by gameplay movement later
    boolean hasShield();   // used by collision later
    String getName();      // for logging messages
}
