package jrowley.gamecontrollib.game_control;

import android.app.Activity;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import jrowley.gamecontrollib.screen_control.ScreenController;
import jrowley.gamecontrollib.touch_handling.MultiTouchHandler;
import jrowley.gamecontrollib.touch_handling.TouchHandler;

/**
 * Created by jrowley on 11/2/15.
 */
public class BaseGameController implements GameController {
    private Activity context;

    private ScreenController screenController;

    private SurfaceView surfaceView;

    private Thread thread;
    private long currentTime, lastTime, deltaTime;
    private volatile boolean shouldContinue;

    private TouchHandler touchHandler;
    private SoundPool soundPool;

    public BaseGameController(SurfaceView surfaceView, Activity activity, TouchHandler touchHandler) {
        this.context = activity;
        this.surfaceView = surfaceView;
        this.touchHandler = touchHandler;
        if(Build.VERSION.SDK_INT < 21) {
            soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        } else {
            soundPool = new SoundPool.Builder().setMaxStreams(20).setAudioAttributes(new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()).build();
        }
        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onActivityResume(Activity activity) {
        shouldContinue = true;
        thread = new Thread(mainLooper);
        thread.start();
    }

    @Override
    public void onActivityPause(Activity activity) {
        shouldContinue = false;
        boolean isStopped = false;
        while(!isStopped) {
            try {
                thread.join();
                isStopped = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

    @Override
    public SurfaceHolder getSurfaceHolder() {
        return surfaceView.getHolder();
    }

    private Runnable mainLooper = new Runnable() {
        @Override
        public void run() {
            lastTime = System.currentTimeMillis();
            while(shouldContinue) {
                currentTime = System.currentTimeMillis();
                deltaTime = currentTime - lastTime;
                if(screenController != null) {
                    screenController.update(deltaTime, BaseGameController.this);
                    screenController.present(surfaceView.getHolder());
                }
                lastTime = currentTime;
            }
        }
    };

    @Override
    public TouchHandler getTouchHandler() {
        return touchHandler;
    }

    @Override
    public SoundPool getSoundPool() {
        return soundPool;
    }

    @Override
    public Context getContext() {
        return context;
    }
}
