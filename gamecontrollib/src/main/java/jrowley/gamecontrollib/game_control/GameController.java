package jrowley.gamecontrollib.game_control;

import android.app.Activity;
import android.content.Context;
import android.media.SoundPool;
import android.view.SurfaceHolder;

import jrowley.gamecontrollib.audio.Audio;
import jrowley.gamecontrollib.graphics.Graphics;
import jrowley.gamecontrollib.input.Input;
import jrowley.gamecontrollib.io_control.FileIO;
import jrowley.gamecontrollib.screen_control.ScreenController;
import jrowley.gamecontrollib.input.TouchHandler;

/**
 * Created by jrowley on 11/2/15.
 */
public interface GameController {
    public Input getInput();
    public FileIO getFileIO();
    public Graphics getGraphics();
    public Audio getAudio();
    public void setScreen(ScreenController screen);
    public ScreenController getCurrentScreen();
    public ScreenController getStartScreen();
    public String getStringResource(int resourceId);
}
