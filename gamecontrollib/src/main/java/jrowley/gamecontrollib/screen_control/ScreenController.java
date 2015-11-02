package jrowley.gamecontrollib.screen_control;

import android.view.SurfaceHolder;

import jrowley.gamecontrollib.game_control.GameController;

/**
 * Created by jrowley on 11/2/15.
 */
public interface ScreenController {
    public void update(long deltaTime, GameController gameController);
    public void present(SurfaceHolder surfaceHolder);
}
