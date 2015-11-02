package jrowley.gamecontrollib.input;

import java.util.List;

/**
 * Created by jrowley on 11/2/15.
 */
public interface Input {
    public boolean isTouchDown(int pointer);
    public int getTouchX(int pointer);
    public int getTouchY(int pointer);
    public float getAccelX();
    public float getAccelY();
    public float getAccelZ();
    public List<TouchEvent> getTouchEvents();
}
