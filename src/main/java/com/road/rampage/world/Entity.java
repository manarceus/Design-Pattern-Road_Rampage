package com.road.rampage.world;

import com.road.rampage.util.Rect;
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity implements GameComponent {
    protected double x, y, w, h;
    protected boolean alive = true;

    protected Entity(double x, double y, double w, double h) {
        this.x = x; this.y = y; this.w = w; this.h = h;
    }

    public boolean isAlive() { return alive; }
    public void kill() { alive = false; }

    public Rect getBounds() { return new Rect(x, y, w, h); }

    @Override public abstract void update(double dt);
    @Override public abstract void render(GraphicsContext gc);
}
