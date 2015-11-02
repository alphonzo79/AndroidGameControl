package jrowley.gamecontrollib.touch_handling;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import jrowley.gamecontrollib.pooling.ObjectPool;

/**
 * Created by jrowley on 11/2/15.
 */
public class MultiTouchHandler implements TouchHandler, View.OnTouchListener {
    private final int MAX_TOUCHPOINTS = 10;
    private boolean[] isTouched = new boolean[MAX_TOUCHPOINTS];
    private int[] touchX = new int[MAX_TOUCHPOINTS];
    private int[] touchY = new int[MAX_TOUCHPOINTS];
    private int[] id = new int[MAX_TOUCHPOINTS];
    private ObjectPool<TouchEvent> touchEventPool;
    private List<TouchEvent> touchEvents = new ArrayList<>();
    private List<TouchEvent> touchEventsBuffer = new ArrayList<>();
    private float scaleX;
    private float scaleY;

    public MultiTouchHandler(View view, float scaleX, float scaleY) {
        touchEventPool = new ObjectPool<TouchEvent>(new ObjectPool.PoolObjectFactory<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        }, 100);
        view.setOnTouchListener(this);

        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            int action = event.getAction() & MotionEvent.ACTION_MASK;
            int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            int pointerCount = event.getPointerCount();
            TouchEvent touchEvent;
            for(int i = 0; i < MAX_TOUCHPOINTS; i++) {
                if(i >= pointerCount) {
                    isTouched[i] = false;
                    id[i] = -1;
                    continue;
                }

                int pointerId = event.getPointerId(i);
                if(event.getAction() != MotionEvent.ACTION_MOVE && i != pointerIndex) {
                    continue;
                }

                switch(action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_POINTER_DOWN:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.setType(TouchEvent.TOUCH_DOWN);
                        touchEvent.setPointer(pointerId);
                        touchEvent.setX(touchX[i] = (int) (event.getX(i) * scaleX));
                        touchEvent.setY(touchY[i] = (int) (event.getY(i) * scaleY));
                        isTouched[i] = true;
                        id[i] = pointerId;
                        touchEventsBuffer.add(touchEvent);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_CANCEL:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.setType(TouchEvent.TOUCH_UP);
                        touchEvent.setPointer(pointerId);
                        touchEvent.setX(touchX[i] = (int)(event.getX(i) * scaleX));
                        touchEvent.setY(touchY[i] = (int)(event.getY(i) * scaleY));
                        isTouched[i] = false;
                        id[i] = -1;
                        touchEventsBuffer.add(touchEvent);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        touchEvent = touchEventPool.newObject();
                        touchEvent.setType(TouchEvent.TOUCH_DRAGGED);
                        touchEvent.setPointer(pointerId);
                        touchEvent.setX(touchX[i] = (int)(event.getX(i) * scaleX));
                        touchEvent.setY(touchY[i] = (int)(event.getY(i) * scaleY));
                        isTouched[i] = true;
                        id[i] = pointerId;
                        touchEventsBuffer.add(touchEvent);
                        break;
                }
            }

            return true;
        }
    }

    @Override
    public boolean isTouchDown(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if(index < 0 || index >= MAX_TOUCHPOINTS) {
                return false;
            } else {
                return isTouched[index];
            }
        }
    }

    @Override
    public int getTouchX(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if(index < 0 || index >= MAX_TOUCHPOINTS) {
                return 0;
            } else {
                return touchX[index];
            }
        }
    }

    @Override
    public int getTouchY(int pointer) {
        synchronized (this) {
            int index = getIndex(pointer);
            if(index < 0 || index >= MAX_TOUCHPOINTS) {
                return 0;
            } else {
                return touchY[index];
            }
        }
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = touchEvents.size();
            for(int i = 0; i < len; i++) {
                touchEventPool.free(touchEvents.get(i));
            }
            touchEvents.clear();
            touchEvents.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();

            return touchEvents;
        }
    }

    private int getIndex(int pointerId) {
        for(int i = 0; i < MAX_TOUCHPOINTS; i++) {
            if(id[i] == pointerId) {
                return i;
            }
        }
        return -1;
    }
}
