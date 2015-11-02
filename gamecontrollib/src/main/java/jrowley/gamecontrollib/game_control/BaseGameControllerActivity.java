package jrowley.gamecontrollib.game_control;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Window;
import android.view.WindowManager;

import jrowley.gamecontrollib.audio.Audio;
import jrowley.gamecontrollib.audio.BaseAudio;
import jrowley.gamecontrollib.graphics.BaseGraphics;
import jrowley.gamecontrollib.graphics.Graphics;
import jrowley.gamecontrollib.input.BaseInput;
import jrowley.gamecontrollib.input.Input;
import jrowley.gamecontrollib.io_control.BasicFileIO;
import jrowley.gamecontrollib.io_control.FileIO;
import jrowley.gamecontrollib.screen_control.ScreenController;

/**
 * Created by jrowley on 11/2/15.
 */
public abstract class BaseGameControllerActivity extends Activity implements GameController {
    AndroidFastRenderView renderView;
    Graphics graphics;
    Audio audio;
    Input input;
    FileIO fileIO;
    ScreenController screen;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        int frameBufferWidth = isLandscape ? 480 : 320;
        int frameBufferHeight = isLandscape ? 320 : 480;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth,
                frameBufferHeight, Bitmap.Config.RGB_565);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        float scaleX = (float) frameBufferWidth / size.x;
        float scaleY = (float) frameBufferHeight / size.y;

        renderView = new AndroidFastRenderView(this, frameBuffer);
        graphics = new BaseGraphics(getAssets(), frameBuffer);
        fileIO = new BasicFileIO(this);
        audio = new BaseAudio(this);
        input = new BaseInput(this, renderView, scaleX, scaleY);
        screen = getStartScreen();
        setContentView(renderView);
    }

    @Override
    public void onResume() {
        super.onResume();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        renderView.pause();
        screen.pause();

        if (isFinishing())
            screen.dispose();
    }

    public Input getInput() {
        return input;
    }

    public FileIO getFileIO() {
        return fileIO;
    }

    public Graphics getGraphics() {
        return graphics;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setScreen(ScreenController screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public ScreenController getCurrentScreen() {
        return screen;
    }
}
