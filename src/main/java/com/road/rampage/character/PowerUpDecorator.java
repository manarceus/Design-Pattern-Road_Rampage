package com.road.rampage.character;

import com.road.rampage.core.GameLogger;
import javafx.scene.canvas.GraphicsContext;

public abstract class PowerUpDecorator implements Character {

    private Character inner;

    protected PowerUpDecorator(Character inner) {
        this.inner = inner;
        GameLogger.getInstance().decorator(getClass().getSimpleName() + " applied to " + inner.getName());
    }

    public Character unwrap() {
        return inner;
    }

    // IMPORTANT: allow manager to reconnect chain without touching fields
    public void setInner(Character inner) {
        this.inner = inner;
    }

    @Override public void update(double dt) { inner.update(dt); }
    @Override public void render(GraphicsContext gc) { inner.render(gc); }

    @Override public int getSpeed() { return inner.getSpeed(); }
    @Override public boolean hasShield() { return inner.hasShield(); }
    @Override public String getName() { return inner.getName(); }
}
