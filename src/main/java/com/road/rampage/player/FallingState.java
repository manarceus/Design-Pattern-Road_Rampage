package com.road.rampage.player;

import java.util.Set;
import javafx.scene.input.KeyCode;

public class FallingState implements PlayerState {
    @Override public void enter(Player player) {}
    @Override public void exit(Player player) {}

    @Override
    public void handleInput(Player player, Set<KeyCode> keys) { }

    @Override
    public void update(Player player, double dt) {
        player.applyGravity(dt);
        player.integrate(dt);

        if (player.isOnLane()) {
            player.clampToLane();
            player.setState(new OnCarState());
        }
    }

    @Override
    public String getName() { return "FALLING"; }
}
