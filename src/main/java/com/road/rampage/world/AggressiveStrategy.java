package com.road.rampage.world;

import java.util.Random;

public class AggressiveStrategy implements DrivingStrategy {
    private final Random rng = new Random();

    @Override
    public LaneIndex chooseNextLane(LaneIndex current) {
        // 80% chance to change lane aggressively
        if (rng.nextDouble() < 0.8) {
            return rng.nextBoolean() ? current.left() : current.right();
        }
        return current;
    }

    @Override
    public double getSpeedMultiplier() {
        return 1.4 + rng.nextDouble() * 0.6; // 1.4-2.0x speed
    }
}
