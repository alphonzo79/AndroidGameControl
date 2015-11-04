package jrowley.gamecontrollib.screen_control;

import android.view.SurfaceHolder;

import jrowley.gamecontrollib.game_control.GameController;

/**
 * Created by jrowley on 11/2/15.
 */
public abstract class ScreenController {
    protected final GameController gameController;

    public ScreenController(GameController gameController) {
        this.gameController = gameController;
    }

    public abstract void update(float deltaTime);
    public abstract void present(float deltaTime);
    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();

    public boolean onBackPressed() {
        return false;
    }
}
