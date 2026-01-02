package com.road.rampage.player;

import com.road.rampage.character.BasePlayerCharacter;
import com.road.rampage.character.Character;
import com.road.rampage.core.GameLogger;
import com.road.rampage.powerup.PowerUpManager;
import com.road.rampage.util.Rect;
import com.road.rampage.world.LaneIndex;
import com.road.rampage.world.Road;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

import java.util.Set;

public class Player {

    // FIXED: current/target lanes for smooth switching
    private LaneIndex currentLane = LaneIndex.MIDDLE;
    private LaneIndex targetLane = LaneIndex.MIDDLE;

    private final double x = 140; // fixed x for endless runner

    // Y / jump physics
    private double y;
    private double vy;

    private final double gravity = 1700;
    private final double jumpVelocity = -750;

    // hitbox
    private final double w = 50;
    private final double h = 30;

    private PlayerState state;

    // Decorators + LIMITED power-ups
    private final PowerUpManager powerUps;
    private final BasePlayerCharacter baseCharacter;

    // Road reference
    private Road road;

    // NEW: player hit points (3 hits -> game over)
    private int hitsRemaining = 3;

    public Player(double startXIgnored, double startY) {
        this.y = startY;

        Character base = new BasePlayerCharacter("Player", x, y);
        this.baseCharacter = (BasePlayerCharacter) base;
        this.powerUps = new PowerUpManager(baseCharacter);

        setState(new OnCarState());
    }

    public void bindRoad(Road road) {
        this.road = road;
        snapToLane();
    }

    private void snapToLane() {
        if (road == null) return;
        double laneCenterY = road.getLaneY(targetLane);
        // DEBUG: log snapping information
        GameLogger.getInstance().info("Player.snapToLane: target=" + targetLane + " laneCenterY=" + laneCenterY + " playerY(before)=" + y);
        this.y = laneCenterY - h / 2.0;
        this.currentLane = targetLane;
        this.vy = 0;
        GameLogger.getInstance().info("Player.snapToLane: current=" + currentLane + " playerY(after)=" + y);
    }

    // --- State pattern ---
    public void setState(PlayerState newState) {
        String from = (state == null) ? "NONE" : state.getName();
        String to = newState.getName();

        if (state != null) state.exit(this);
        state = newState;
        state.enter(this);

        GameLogger.getInstance().stateChange("Player", from, to);
    }

    public void handleInput(Set<KeyCode> keys) {
        if (state != null) state.handleInput(this, keys);
    }

    public void update(double dt) {
        // Update active character (timed decorators) so timers progress
        Character active = powerUps.getCharacter();
        if (active != null) active.update(dt);

        if (state != null) state.update(this, dt);

        // sync render position
        baseCharacter.setPosition(x, y);
    }

    public void render(GraphicsContext gc) {
        powerUps.getCharacter().render(gc);
    }

    // Power-ups API (limited uses)
    public void applySpeedBoost(double seconds) {
        powerUps.applySpeedBoost(seconds);
    }
    public void applyShield(double seconds) {
        powerUps.applyShield(seconds);
    }

    // Runner actions
    public void moveLaneLeft() {
        GameLogger.getInstance().info("Player.moveLaneLeft: before target=" + targetLane + " current=" + currentLane);
        // compute new target from the target lane (consistent with original behavior)
        targetLane = targetLane.left();
        GameLogger.getInstance().info("Player.moveLaneLeft: after target=" + targetLane + " current=" + currentLane);
        snapToLane();
    }

    public void moveLaneRight() {
        GameLogger.getInstance().info("Player.moveLaneRight: before target=" + targetLane + " current=" + currentLane);
        // compute new target from the target lane (consistent with original behavior)
        targetLane = targetLane.right();
        GameLogger.getInstance().info("Player.moveLaneRight: after target=" + targetLane + " current=" + currentLane);
        snapToLane();
    }

    // NEW: take a hit; returns true if player died (no hits left)
    public boolean takeHit() {
        // if a shield is active, consume it and don't decrement hits
        if (powerUps.isShieldActive()) {
            powerUps.consumeActiveDecoratorIfShield();
            return false; // shield absorbed hit
        }

        hitsRemaining -= 1;
        GameLogger.getInstance().info("Player took a hit! hitsRemaining=" + hitsRemaining);
        return hitsRemaining <= 0;
    }

    public int getHitsRemaining() { return hitsRemaining; }

    // Jump physics
    public void applyGravity(double dt) { vy += gravity * dt; }
    public void integrate(double dt) { y += vy * dt; }

    public boolean isOnLane() {
        if (road == null) return false;
        double laneCenterY = road.getLaneY(currentLane);
        double laneTop = laneCenterY - h / 2.0;
        return y >= laneTop;
    }

    public void clampToLane() {
        if (road == null) return;
        double laneCenterY = road.getLaneY(currentLane);
        y = laneCenterY - h / 2.0;
        vy = 0;
    }

    public Rect getBounds() {
        return new Rect(x, y, w, h);
    }

    public LaneIndex getLane() { return currentLane; }

    // Power-up getters for HUD
    public PowerUpManager getPowerUpManager() { return powerUps; }

    // Physics getters/setters
    public double getJumpVelocity() { return jumpVelocity; }
    public void setVy(double vy) { this.vy = vy; }
    public double getVy() { return vy; }

    public boolean hasShield() { return powerUps.isShieldActive(); }
}
