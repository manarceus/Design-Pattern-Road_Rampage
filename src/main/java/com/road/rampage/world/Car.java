package com.road.rampage.world;

import com.road.rampage.core.GameLogger;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Car extends Entity {
    private DrivingStrategy strategy;
    private LaneIndex targetLane;
    private final double baseSpeed;

    public Car(double x, double y, double w, double h, double speed, DrivingStrategy strategy) {
        super(x, y, w, h);
        this.baseSpeed = speed;
        this.strategy = strategy;
        this.targetLane = LaneIndex.MIDDLE;
        GameLogger.getInstance().info("Entity created: Car with " + strategy.getClass().getSimpleName());
    }

    @Override
    public void update(double dt) {
        // horizontal speed
        double speed = baseSpeed * strategy.getSpeedMultiplier();
        x -= speed * dt;

        // lane switching (strategy-driven)
        // previously compared targetLane to itself (bug). Ask strategy for the next lane
        // and update only if it differs.
        LaneIndex next = strategy.chooseNextLane(targetLane);
        if (next != targetLane) {
            targetLane = next;
        }

        // off-screen
        if (x + w < -50) alive = false;
    }

    @Override
    public void render(GraphicsContext gc) {
        Color color = switch (strategy.getClass().getSimpleName()) {
            case "AggressiveStrategy" -> Color.RED;
            case "CautiousStrategy" -> Color.GREEN;
            default -> Color.ORANGE;
        };
        gc.setFill(color);
        gc.fillRect(x, y, w, h);
    }
}
