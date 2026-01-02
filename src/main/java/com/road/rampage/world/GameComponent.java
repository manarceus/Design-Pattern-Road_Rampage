package com.road.rampage.world;

import javafx.scene.canvas.GraphicsContext;

public interface GameComponent {
    void update(double dt);
    void render(GraphicsContext gc);

    default void add(GameComponent c) { throw new UnsupportedOperationException(); }
    default void remove(GameComponent c) { throw new UnsupportedOperationException(); }
}
