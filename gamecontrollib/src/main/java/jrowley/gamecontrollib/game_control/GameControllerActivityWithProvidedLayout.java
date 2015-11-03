package jrowley.gamecontrollib.game_control;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.Log;

import jrowley.gamecontrollib.R;

/**
 * Created by jrowley on 11/3/15.
 */
public abstract class GameControllerActivityWithProvidedLayout extends BaseGameControllerActivity implements AndroidFastRenderView.OnMeasureListener {
    private boolean hasResumed = false;

    @Override
    AndroidFastRenderView setUpContentView() {
        setContentView(getLayoutResourceId());

        AndroidFastRenderView fastRenderView = (AndroidFastRenderView)findViewById(getFastRenderViewId());
        fastRenderView.setGame(this);
        fastRenderView.setOnMeasureListener(this);

        return fastRenderView;
    }

    @Override
    public void onMeasure() {
        int frameBufferWidth = renderView.getMeasuredWidth();
        int frameBufferHeight = renderView.getMeasuredHeight();
        if(!setupComplete && frameBufferWidth > 0) {
            Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Bitmap.Config.RGB_565);
            renderView.setFramebuffer(frameBuffer);
            completeSetup();

            if(hasResumed) {
                screen.resume();
                renderView.resume();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hasResumed = true;
    }

    protected abstract int getLayoutResourceId();
    protected abstract int getFastRenderViewId();
}
