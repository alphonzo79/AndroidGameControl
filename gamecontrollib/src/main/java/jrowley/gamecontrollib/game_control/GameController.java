package jrowley.gamecontrollib.game_control;

import android.app.Activity;
import android.content.Context;
import android.media.SoundPool;
import android.view.SurfaceHolder;

import jrowley.gamecontrollib.screen_control.ScreenController;
import jrowley.gamecontrollib.touch_handling.TouchHandler;

/**
 * Created by jrowley on 11/2/15.
 */
public interface GameController {
    public TouchHandler getTouchHandler();
    public void setScreenController(ScreenController newScreen);
    public void onActivityPause(Activity activity);
    public void onActivityResume(Activity activity);
    public SurfaceHolder getSurfaceHolder();
    public SoundPool getSoundPool();
    public Context getContext();
}
