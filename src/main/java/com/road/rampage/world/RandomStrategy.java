package com.road.rampage.world;

import java.util.Random;

public class RandomStrategy implements DrivingStrategy {
    private final Random rng = new Random();

    @Override
    public LaneIndex chooseNextLane(LaneIndex current) {
        return LaneIndex.values()[rng.nextInt(3)];
    }

    @Override
    public double getSpeedMultiplier() {
        return 0.9 + rng.nextDouble() * 0.4; // 0.9-1.3x
    }
}
