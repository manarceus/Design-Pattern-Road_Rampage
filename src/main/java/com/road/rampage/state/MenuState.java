package com.road.rampage.state;

import com.road.rampage.core.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import java.io.File;

public class MenuState implements GameState {

    private Image bgImage = null;

    @Override public void enter() {
        // Attempt to load background image from absolute path; fallback to solid color if unavailable
        try {
            File f = new File("C:\\Users\\alaaj\\Desktop\\Education\\2eme_ing\\Design_pattern\\Project\\Road_Rampage\\roadrampage\\Background.png");
            if (f.exists()) {
                bgImage = new Image(f.toURI().toString());
            }
        } catch (Exception ex) {
            // ignore and fallback to solid color background
        }
    }
    @Override public void exit() {}
    @Override public void update(double dt) {}

    @Override
    public void render(GraphicsContext gc) {
        if (bgImage != null) {
            gc.drawImage(bgImage, 0, 0, 800, 600);
        } else {
            gc.setFill(Color.WHITE);
            gc.fillRect(0, 0, 800, 600);
        }

      //  gc.setFill(Color.BLACK);
       // gc.fillText("ROAD RAMPAGE", 40, 60);
      //  gc.fillText("ENTER: Start", 40, 95);
       // gc.fillText("ESC: Quit", 40, 125);
    }

    @Override
    public void onKeyPressed(KeyCode code) {
        if (code == KeyCode.ENTER) {
            Game.getInstance().setState(new PlayingState());
        } else if (code == KeyCode.ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public void onKeyReleased(KeyCode code) {
        // no-op
    }

    @Override
    public String getName() {
        return "MENU";
    }
}
