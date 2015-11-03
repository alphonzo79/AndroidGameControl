package jrowley.gamecontrollib.game_control;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by jrowley on 11/2/15.
 */
public class AndroidFastRenderView extends SurfaceView implements Runnable {
    BaseGameControllerActivity game;
    Bitmap framebuffer;
    Thread renderThread = null;
    SurfaceHolder holder;
    volatile boolean running = false;

    private OnMeasureListener onMeasureListener;

    public AndroidFastRenderView(Context context) {
        super(context);
        this.holder = getHolder();
    }

    public AndroidFastRenderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.holder = getHolder();
    }

    public AndroidFastRenderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.holder = getHolder();
    }

    public AndroidFastRenderView(BaseGameControllerActivity game, Bitmap framebuffer) {
        super(game);
        this.game = game;
        this.framebuffer = framebuffer;
        this.holder = getHolder();
    }

    public void setGame(BaseGameControllerActivity game) {
        this.game = game;
    }

    public void setFramebuffer(Bitmap framebuffer) {
        this.framebuffer = framebuffer;
    }

    public Bitmap getFramebuffer() {
        return this.framebuffer;
    }

    public void resume() {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    public void run() {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while(running) {
            if(!holder.getSurface().isValid())
                continue;

            float deltaTime = (System.nanoTime()-startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            game.getCurrentScreen().update(deltaTime);
            game.getCurrentScreen().present(deltaTime);

            Canvas canvas = holder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(framebuffer, null, dstRect, null);
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        running = false;
        while(true) {
            try {
                renderThread.join();
                return;
            } catch (InterruptedException e) {
                // retry
            }
        }
    }

    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(onMeasureListener != null) {
            onMeasureListener.onMeasure();
        }
    }

    public void setOnMeasureListener(OnMeasureListener onMeasureListener) {
        this.onMeasureListener = onMeasureListener;
    }

    public interface OnMeasureListener {
        public void onMeasure();
    }
}
