package com.road.rampage.core;

import com.road.rampage.state.GameState;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements Subject {  // ✅ OBSERVER: Game is Subject

    private static final Game INSTANCE = new Game();

    private GraphicsContext gc;
    private GameState state;

    // OBSERVER: list of HUD/Score observers
    private final List<Observer> observers = new CopyOnWriteArrayList<>();

    private Game() {}

    public static Game getInstance() {
        return INSTANCE;
    }

    public void init(GraphicsContext gc, GameState initialState) {
        this.gc = gc;

        GameLogger.getInstance().info("Game started");

        setState(initialState);
    }

    public void setState(GameState newState) {
        String from = (state == null) ? "NONE" : state.getName();
        String to   = (newState == null) ? "NONE" : newState.getName();

        if (state != null) state.exit();
        state = newState;
        state.enter();

        GameLogger.getInstance().stateChange("Game", from, to);
    }

    public void update(double dt) {
        if (state != null) state.update(dt);
    }

    public void render() {
        if (state != null) state.render(gc);
    }

    public void onKeyPressed(KeyCode code) {
        if (state != null) state.onKeyPressed(code);
    }

    public void onKeyReleased(KeyCode code) {
        if (state != null) state.onKeyReleased(code);
    }

    // ========== OBSERVER PATTERN ==========
    @Override
    public void addObserver(Observer o) {
        if (o == null) return;
        observers.add(o);
        GameLogger.getInstance().info("OBSERVER added: " + o.getClass().getSimpleName());
    }

    @Override
    public void removeObserver(Observer o) {
        if (o == null) return;
        observers.remove(o);
    }

    @Override
    public void notifyObservers(EventType event, Object data) {
        for (Observer o : observers) {
            try {
                o.onNotify(event, data);
            } catch (Exception ex) {
                GameLogger.getInstance().info("Observer error: " + ex.getMessage());
            }
        }
    }
}
