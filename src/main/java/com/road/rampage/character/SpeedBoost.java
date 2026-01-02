package com.road.rampage.character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SpeedBoost extends PowerUpDecorator {

    public SpeedBoost(Character inner) {
        super(inner);
    }

    @Override
    public int getSpeed() {
        return (int) (super.getSpeed() * 1.8);
    }

    @Override
    public void render(GraphicsContext gc) {
        if (unwrap() instanceof BasePlayerCharacter base) {
            javafx.scene.paint.Color old = base.getTint();
            base.setTint(Color.ORANGERED);
            base.render(gc);
            base.setTint(old);
        } else {
            super.render(gc);
        }
    }
}
