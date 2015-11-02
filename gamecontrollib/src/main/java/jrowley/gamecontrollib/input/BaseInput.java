package jrowley.gamecontrollib.input;

import android.content.Context;
import android.view.View;

import java.util.List;

/**
 * Created by jrowley on 11/2/15.
 */
public class BaseInput implements Input {
    AccelerometerHandler accelHandler;
    TouchHandler touchHandler;

    public BaseInput(Context context, View view, float scaleX, float scaleY) {
        accelHandler = new AccelerometerHandler(context);
        touchHandler = new MultiTouchHandler(view, scaleX, scaleY);
    }

    public boolean isTouchDown(int pointer) {
        return touchHandler.isTouchDown(pointer);
    }

    public int getTouchX(int pointer) {
        return touchHandler.getTouchX(pointer);
    }

    public int getTouchY(int pointer) {
        return touchHandler.getTouchY(pointer);
    }

    public float getAccelX() {
        return accelHandler.getAccelX();
    }

    public float getAccelY() {
        return accelHandler.getAccelY();
    }

    public float getAccelZ() {
        return accelHandler.getAccelZ();
    }

    public List<TouchEvent> getTouchEvents() {
        return touchHandler.getTouchEvents();
    }
}
