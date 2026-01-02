package com.road.rampage.world;

import com.road.rampage.core.GameLogger;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lane extends CompositeNode {

    private final Random rng = new Random();
    private final double laneY;
    private final double laneHeight;

    private double spawnTimer = 0;

    public Lane(double laneY, double laneHeight) {
        this.laneY = laneY;
        this.laneHeight = laneHeight;
        this.spawnTimer = 0.6 + rng.nextDouble() * 0.8;
    }

    public List<Car> getCars() {
        List<Car> cars = new ArrayList<>();
        for (GameComponent c : children) {
            if (c instanceof Car car) cars.add(car);
        }
        return cars;
    }

    @Override
    public void update(double dt) {
        spawnTimer -= dt;
        if (spawnTimer <= 0) {
            spawnTimer = 0.6 + rng.nextDouble() * 0.9;

            double carW = 70;
            double carH = laneHeight * 0.55;
            double carY = laneY + (laneHeight - carH) / 2.0;
            double carX = 800 + 20;
            double speed = 240 + rng.nextDouble() * 220;

            // STRATEGY: pick random car behavior
            DrivingStrategy strat = switch ((int)(rng.nextDouble() * 3)) {
                case 0 -> new AggressiveStrategy();
                case 1 -> new CautiousStrategy();
                default -> new RandomStrategy();
            };

            add(new Car(carX, carY, carW, carH, speed, strat));
            GameLogger.getInstance().info("Entity created: Car with " + strat.getClass().getSimpleName());
        }

        // update children
        super.update(dt);

        // remove dead
        List<GameComponent> copy = new ArrayList<>(children);
        for (GameComponent c : copy) {
            if (c instanceof Entity e && !e.isAlive()) {
                remove(c);
                GameLogger.getInstance().info("Entity destroyed: " + c.getClass().getSimpleName());
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
