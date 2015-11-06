package jrowley.gamecontrollib.graphics;

import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by jrowley on 11/2/15.
 */
public interface Graphics {
    public static enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565
    }

    public Pixmap newPixmap(String fileName, PixmapFormat format);
    public void clear(int color);
    public void drawPixel(int x, int y, int color);
    public void drawLine(int x, int y, int x2, int y2, float width, int color);
    public void drawRect(int left, int top, int width, int height, int color);
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);
    public void drawPixmap(Pixmap pixmap, int x, int y);
    public void writeText(String text, int xAnchorPoint, int yAnchorPoint, int color, float textSize, Typeface typeface, Paint.Align alignment);
    public int getWidth();
    public int getHeight();
    public float getScale();
}
