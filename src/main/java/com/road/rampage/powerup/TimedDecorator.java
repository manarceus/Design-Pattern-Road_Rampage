package com.road.rampage.powerup;

import com.road.rampage.character.Character;
import com.road.rampage.core.GameLogger;
import javafx.scene.canvas.GraphicsContext;

public class TimedDecorator implements Character {
    private Character decorated;
    private double timeLeft;
    private final Character originalBase; // store original to revert

    public TimedDecorator(Character decorated, double duration, Character originalBase) {
        this.decorated = decorated;
        this.timeLeft = duration;
        this.originalBase = originalBase;
    }

    @Override
    public void update(double dt) {
        timeLeft -= dt;
        if (timeLeft <= 0) {
            GameLogger.getInstance().info("DECORATOR Timed effect removed from Player");
            this.decorated = originalBase; // revert to base
        } else {
            decorated.update(dt);
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        decorated.render(gc);
    }

    @Override
    public int getSpeed() {
        return decorated.getSpeed();
    }

    @Override
    public boolean hasShield() {
        return decorated.hasShield();
    }

    @Override
    public String getName() {
        return decorated.getName();
    }
}
