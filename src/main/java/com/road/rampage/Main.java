package com.road.rampage;

import com.road.rampage.core.DebugObserver;
import com.road.rampage.core.Game;
import com.road.rampage.state.MenuState;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new Group(canvas));
        stage.setTitle("Road Rampage");
        stage.setScene(scene);
        stage.show();

        Game.getInstance().init(gc, new MenuState());

        // register a debug observer to see events in logs
        Game.getInstance().addObserver(new DebugObserver());

        scene.setOnKeyPressed(e -> Game.getInstance().onKeyPressed(e.getCode()));
        scene.setOnKeyReleased(e -> Game.getInstance().onKeyReleased(e.getCode()));

        new AnimationTimer() {
            private long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) { last = now; return; }
                double dt = (now - last) / 1_000_000_000.0;
                last = now;

                Game.getInstance().update(dt);
                Game.getInstance().render();
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
