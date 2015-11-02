package jrowley.gamecontrollib.touch_handling;

import java.util.List;

/**
 * Created by jrowley on 11/2/15.
 */
public interface TouchHandler {
    public boolean isTouchDown(int pointer);
    public int getTouchX(int pointer);
    public int getTouchY(int pointer);
    public List<TouchEvent> getTouchEvents();
}
