package com.road.rampage.world;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Road extends CompositeNode {

    private final double x = 0;
    private final double y = 200;
    private final double w = 800;
    private final double h = 300;

    private final Lane left;
    private final Lane middle;
    private final Lane right;

    public Road() {
        double laneH = h / 3.0;

        left = new Lane(y + 0 * laneH, laneH);
        middle = new Lane(y + 1 * laneH, laneH);
        right = new Lane(y + 2 * laneH, laneH);

        add(left);
        add(middle);
        add(right);
    }

    public double getLaneY(LaneIndex lane) {
        double laneH = h / 3.0;
        return (y + lane.idx * laneH) + laneH / 2.0;
    }

    public List<Car> getCarsInLane(LaneIndex lane) {
        return switch (lane) {
            case LEFT -> left.getCars();
            case MIDDLE -> middle.getCars();
            case RIGHT -> right.getCars();
        };
    }

    @Override
    public void render(GraphicsContext gc) {
        // road background
        gc.setFill(Color.DIMGRAY);
        gc.fillRect(x, y, w, h);

        // lane separators
        gc.setStroke(Color.WHITE);
        gc.setLineDashes(10);
        gc.strokeLine(0, y + h / 3.0, 800, y + h / 3.0);
        gc.strokeLine(0, y + 2 * h / 3.0, 800, y + 2 * h / 3.0);
        gc.setLineDashes(null);

        super.render(gc);
    }
}
