package com.road.rampage.world;

import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class CompositeNode implements GameComponent {
    protected final List<GameComponent> children = new ArrayList<>();

    @Override public void add(GameComponent c) { children.add(c); }
    @Override public void remove(GameComponent c) { children.remove(c); }

    @Override
    public void update(double dt) {
        // iterate copy to avoid concurrent modification if child removes itself
        List<GameComponent> copy = new ArrayList<>(children);
        for (GameComponent c : copy) c.update(dt);
    }

    @Override
    public void render(GraphicsContext gc) {
        for (GameComponent c : children) c.render(gc);
    }
}
