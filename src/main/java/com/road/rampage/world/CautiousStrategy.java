package com.road.rampage.world;

public class CautiousStrategy implements DrivingStrategy {
    @Override
    public LaneIndex chooseNextLane(LaneIndex current) {
        return current; // never changes lane
    }

    @Override
    public double getSpeedMultiplier() {
        return 0.6; // slow
    }
}
