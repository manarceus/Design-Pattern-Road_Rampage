package com.road.rampage.world;

import javafx.scene.canvas.GraphicsContext;

public class Level extends CompositeNode {

    private final Road road = new Road();

    public Level() {
        add(road);
    }

    public Road getRoad() {
        return road;
    }

    @Override
    public void update(double dt) {
        super.update(dt);
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
