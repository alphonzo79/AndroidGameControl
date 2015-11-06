package jrowley.gamecontrollib.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jrowley on 11/2/15.
 */
public class BaseGraphics implements Graphics {
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();
    float scaleRatio;

    public BaseGraphics(Context context, AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();

        scaleRatio = context.getResources().getDisplayMetrics().density;
    }

    /**
     *
     * @param fileName
     * @param format
     * @return
     */
    @Override
    public Pixmap newPixmap(String fileName, PixmapFormat format) {
        Bitmap.Config config = null;
        if (format == PixmapFormat.RGB565)
            config = Bitmap.Config.RGB_565;
        else if (format == PixmapFormat.ARGB4444)
            config = Bitmap.Config.ARGB_4444;
        else
            config = Bitmap.Config.ARGB_8888;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        } catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }

        if (bitmap.getConfig() == Bitmap.Config.RGB_565)
            format = PixmapFormat.RGB565;
        else if (bitmap.getConfig() == Bitmap.Config.ARGB_4444)
            format = PixmapFormat.ARGB4444;
        else
            format = PixmapFormat.ARGB8888;

        return new BasePixmap(bitmap, format);
    }

    /**
     *
     * @param color
     */
    @Override
    public void clear(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    /**
     *
     * @param x
     * @param y
     * @param color
     */
    @Override
    public void drawPixel(int x, int y, int color) {
        paint.reset();
        paint.setColor(color);
        canvas.drawPoint(x, y, paint);
    }

    /**
     *
     * @param x
     * @param y
     * @param x2
     * @param y2
     * @param color
     */
    @Override
    public void drawLine(int x, int y, int x2, int y2, float width, int color) {
        paint.reset();
        paint.setColor(color);
        paint.setStrokeWidth(width);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    /**
     *
     * @param left
     * @param top
     * @param width
     * @param height
     * @param color
     */
    @Override
    public void drawRect(int left, int top, int width, int height, int color) {
        paint.reset();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(left, top, left + width - 1, top + height - 1, paint);
    }

    /**
     *
     * @param pixmap
     * @param x
     * @param y
     * @param srcX
     * @param srcY
     * @param srcWidth
     * @param srcHeight
     */
    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y, int srcX, int srcY,
                           int srcWidth, int srcHeight) {
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth - 1;
        srcRect.bottom = srcY + srcHeight - 1;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth - 1;
        dstRect.bottom = y + srcHeight - 1;

        canvas.drawBitmap(((BasePixmap) pixmap).bitmap, srcRect, dstRect, null);
    }

    /**
     *
     * @param pixmap
     * @param x
     * @param y
     */
    @Override
    public void drawPixmap(Pixmap pixmap, int x, int y) {
        canvas.drawBitmap(((BasePixmap) pixmap).bitmap, x, y, null);
    }

    @Override
    public void writeText(String text, int xAnchorPoint, int yAnchorPoint, int color, float textSize, Typeface typeface, Paint.Align alignment) {
        paint.reset();
        paint.setColor(color);
        paint.setTextSize(textSize);
        paint.setTypeface(typeface);
        paint.setTextAlign(alignment);
        canvas.drawText(text, xAnchorPoint, yAnchorPoint, paint);
    }

    /**
     *
     * @return
     */
    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    /**
     *
     * @return
     */
    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }

    @Override
    public float getScale() {
        return scaleRatio;
    }
}
