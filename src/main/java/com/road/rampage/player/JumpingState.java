package com.road.rampage.player;

import java.util.Set;
import javafx.scene.input.KeyCode;

public class JumpingState implements PlayerState {
    @Override
    public void enter(Player player) {
        player.setVy(player.getJumpVelocity());
    }

    @Override public void exit(Player player) {}

    @Override
    public void handleInput(Player player, Set<KeyCode> keys) {
        // no-op
    }

    @Override
    public void update(Player player, double dt) {
        player.applyGravity(dt);
        player.integrate(dt);

        if (player.getVy() > 0) {
            player.setState(new FallingState());
        }
    }

    @Override
    public String getName() { return "JUMPING"; }
}
