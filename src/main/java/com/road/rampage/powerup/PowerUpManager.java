package com.road.rampage.powerup;

import com.road.rampage.character.BasePlayerCharacter;
import com.road.rampage.character.Character;
import com.road.rampage.core.Game;
import com.road.rampage.core.GameLogger;
import com.road.rampage.character.SpeedBoost;
import com.road.rampage.character.Shield;

public class PowerUpManager {
    private Character currentCharacter;
    private final BasePlayerCharacter base;

    // LIMITED: 3 uses each
    private int speedBoostUses = 3;
    private int shieldUses = 3;

    public PowerUpManager(BasePlayerCharacter base) {
        this.base = base;
        this.currentCharacter = base;
    }

    public void applySpeedBoost(double duration) {
        if (speedBoostUses <= 0) return;
        speedBoostUses--;
        GameLogger.getInstance().info("DECORATOR SpeedBoost applied to Player (uses left: " + speedBoostUses + ")");

        Character boosted = new SpeedBoost(base);
        currentCharacter = new TimedDecorator(boosted, duration, base);

        // notify observers that a speedboost was used
        Game.getInstance().notifyObservers(com.road.rampage.core.EventType.POWERUP_USED, "SPEEDBOOST");
    }

    public void applyShield(double duration) {
        if (shieldUses <= 0) return;
        shieldUses--;
        GameLogger.getInstance().info("DECORATOR Shield applied to Player (uses left: " + shieldUses + ")");

        Character shielded = new Shield(base);
        currentCharacter = new TimedDecorator(shielded, duration, base);

        // notify observers that a shield was used
        Game.getInstance().notifyObservers(com.road.rampage.core.EventType.POWERUP_USED, "SHIELD");
    }

    // Allow the Player to update the timed decorator chain
    public void update(double dt) {
        if (currentCharacter != null) currentCharacter.update(dt);
    }

    public Character getCharacter() { return currentCharacter; }

    public boolean hasSpeedBoost() { return speedBoostUses > 0; }
    public boolean hasShield() { return shieldUses > 0; }

    public int getSpeedBoostUses() { return speedBoostUses; }
    public int getShieldUses() { return shieldUses; }

    // NEW: check whether a shield is currently active (i.e., the currentCharacter reports hasShield())
    public boolean isShieldActive() {
        return currentCharacter != null && currentCharacter.hasShield() && currentCharacter != base;
    }

    // NEW: consume/clear the active decorator if it provides protection (used when shield blocks a hit)
    public void consumeActiveDecoratorIfShield() {
        if (currentCharacter != null && currentCharacter.hasShield()) {
            GameLogger.getInstance().info("DECORATOR Shield consumed by hit and removed from Player");
            currentCharacter = base;

            // notify observers that a shield was consumed/removed
            Game.getInstance().notifyObservers(com.road.rampage.core.EventType.POWERUP_USED, "SHIELD_CONSUMED");
        }
    }

    // NEW: clear any active decorator (general-purpose)
    public void clearActiveDecorator() {
        currentCharacter = base;
    }
}
