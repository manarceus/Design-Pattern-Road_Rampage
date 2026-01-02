package com.road.rampage.player;

import javafx.scene.input.KeyCode;
import java.util.Set;

public interface PlayerState {
    void enter(Player player);
    void exit(Player player);
    void handleInput(Player player, Set<KeyCode> keys);
    void update(Player player, double dt);
    String getName();
}
