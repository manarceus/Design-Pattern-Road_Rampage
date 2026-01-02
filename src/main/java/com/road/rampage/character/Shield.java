package com.road.rampage.character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Shield extends PowerUpDecorator {

    public Shield(Character inner) {
        super(inner);
    }

    @Override
    public boolean hasShield() {
        return true;
    }

    @Override
    public void render(GraphicsContext gc) {
        // if inner is a BasePlayerCharacter, change its tint while rendering
        if (unwrap() instanceof BasePlayerCharacter base) {
            Color old = base.getTint();
            base.setTint(Color.GOLD);
            base.render(gc);
            base.setTint(old);
        } else {
            super.render(gc);
        }
    }
}
