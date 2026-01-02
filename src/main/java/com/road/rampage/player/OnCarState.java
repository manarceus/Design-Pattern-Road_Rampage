package com.road.rampage.player;

import javafx.scene.input.KeyCode;
import java.util.Set;

public class OnCarState implements PlayerState {

    @Override public void enter(Player player) {}
    @Override public void exit(Player player) {}

    @Override
    public void handleInput(Player player, Set<KeyCode> keys) {
        // Lane movement is handled on key press at the PlayingState level to ensure
        // a single action per press. Here we only handle jump while on car.
        if (keys.contains(KeyCode.SPACE)) {
            player.setState(new JumpingState());
            // don't remove from set here; PlayingState manages the key lifecycle
        }
    }

    @Override
    public void update(Player player, double dt) {
        // stay on lane
        if (!player.isOnLane()) player.clampToLane();
    }

    @Override
    public String getName() {
        return "ON_CAR";
    }
}
