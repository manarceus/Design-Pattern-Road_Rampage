package com.road.rampage.character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BasePlayerCharacter implements Character {

    private final String name;
    private double x, y;
    private Color tint = Color.DODGERBLUE; // default color

    public BasePlayerCharacter(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    @Override public void update(double dt) {}

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(tint);
        gc.fillRect(x, y, 50, 30);
    }

    @Override public int getSpeed() { return 200; }
    @Override public boolean hasShield() { return false; }
    @Override public String getName() { return name; }

    // optional helpers if you want to move later
    public void setPosition(double x, double y) { this.x = x; this.y = y; }
    public double getX() { return x; }
    public double getY() { return y; }

    // NEW: color/tint API
    public void setTint(Color color) { this.tint = color; }
    public Color getTint() { return tint; }
}
