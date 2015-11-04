package jrowley.gamecontrollib.game_control;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
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
import jrowley.gamecontrollib.util.FrameRateTracker;

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
    FrameRateTracker frameRateTracker;

    boolean setupComplete = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        renderView = setUpContentView();
        if(renderView.getFramebuffer() != null) {
            completeSetup();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(setupComplete) {
            screen.resume();
            renderView.resume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(setupComplete) {
            renderView.pause();
            screen.pause();

            if (isFinishing())
                screen.dispose();
        }
    }

    AndroidFastRenderView setUpContentView() {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        int frameBufferWidth = size.x;
        int frameBufferHeight = size.y;
        Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565);

        AndroidFastRenderView view = new AndroidFastRenderView(this, frameBuffer);
        setContentView(view);

        return view;
    }

    void completeSetup() {
        Bitmap frameBuffer = renderView.getFramebuffer();

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        float scaleX = (float) frameBuffer.getWidth() / size.x;
        float scaleY = (float) frameBuffer.getHeight() / size.y;

        graphics = new BaseGraphics(this, getAssets(), frameBuffer);
        fileIO = new BasicFileIO(this);
        audio = new BaseAudio(this);
        input = new BaseInput(this, renderView, scaleX, scaleY);
        screen = getStartScreen();

        frameRateTracker = new FrameRateTracker();

        setupComplete = true;
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

        if(this.screen != null) {
            this.screen.pause();
            this.screen.dispose();
        }
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    public ScreenController getCurrentScreen() {
        return screen;
    }

    @Override
    public String getStringResource(int resourceId) {
        return getString(resourceId);
    }

    @Override
    public FrameRateTracker getFrameRateTracker() {
        return frameRateTracker;
    }

    @Override
    public void onBackPressed() {
        if(screen == null || !screen.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
