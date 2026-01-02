package com.road.rampage.player;

import java.util.Set;
import javafx.scene.input.KeyCode;

public class DeadState implements PlayerState {

    @Override public void enter(Player player) {}
    @Override public void exit(Player player) {}
    @Override public void handleInput(Player player, Set<KeyCode> keys) {}
    @Override public void update(Player player, double dt) {}

    @Override
    public String getName() {
        return "DEAD";
    }
}
