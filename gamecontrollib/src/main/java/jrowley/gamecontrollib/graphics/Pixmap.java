package jrowley.gamecontrollib.graphics;

/**
 * Created by jrowley on 11/2/15.
 */
public interface Pixmap {
    public int getWidth();
    public int getHeight();
    public Graphics.PixmapFormat getFormat();
    public void dispose();
}
