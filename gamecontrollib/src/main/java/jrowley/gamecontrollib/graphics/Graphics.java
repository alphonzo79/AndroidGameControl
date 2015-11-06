package jrowley.gamecontrollib.graphics;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

/**
 * Created by jrowley on 11/2/15.
 */
public interface Graphics {
    public static enum PixmapFormat {
        ARGB8888, ARGB4444, RGB565
    }

    /**
     *
     * @param fileName
     * @param format
     * @return
     */
    public Pixmap newPixmap(String fileName, PixmapFormat format);
    /**
     *
     * @param color New Background color
     */
    public void clear(int color);
    /**
     *
     * @param x x
     * @param y y
     * @param color Color
     */
    public void drawPixel(int x, int y, int color);
    /**
     *
     * @param x x
     * @param y y
     * @param x2 x2
     * @param y2 y2
     * @param color color
     */
    public void drawLine(int x, int y, int x2, int y2, float width, int color);
    /**
     *
     * @param left left
     * @param top top
     * @param width width
     * @param height height
     * @param color color
     */
    public void drawRect(int left, int top, int width, int height, int color);
    /**
     *
     * @param pixmap
     * @param x x
     * @param y y
     * @param srcX srcX
     * @param srcY srcY
     * @param srcWidth srcWidth
     * @param srcHeight srcHeight
     */
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);
    /**
     *
     * @param pixmap
     * @param x x
     * @param y y
     */
    public void drawPixmap(Pixmap pixmap, int x, int y);
    /**
     *
     * @param text
     * @param xAnchorPoint xAnchorPoint
     * @param yBaseline yBaseline
     * @param color color
     * @param textSize textSize
     * @param typeface
     * @param alignment
     */
    public void writeText(String text, int xAnchorPoint, int yBaseline, int color, float textSize, Typeface typeface, Paint.Align alignment);
    /**
     *
     * @param text
     * @param textSize textSize
     * @param typeface
     * @param alignment
     * @param bounds
     */
    public void getTextBounds(String text, float textSize, Typeface typeface, Paint.Align alignment, Rect bounds);
    public int getWidth();
    public int getHeight();
    public float getScale();
}
