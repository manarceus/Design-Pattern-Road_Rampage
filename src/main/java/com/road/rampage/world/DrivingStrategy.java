package com.road.rampage.world;

public interface DrivingStrategy {
    LaneIndex chooseNextLane(LaneIndex current);
    double getSpeedMultiplier();
}
