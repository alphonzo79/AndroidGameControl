package jrowley.gamecontrollib.graphics;

import android.graphics.Bitmap;
import jrowley.gamecontrollib.graphics.Graphics.PixmapFormat;

/**
 * Created by jrowley on 11/2/15.
 */
public class BasePixmap implements Pixmap {
    Bitmap bitmap;
    PixmapFormat format;

    public BasePixmap(Bitmap bitmap, PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    public int getWidth() {
        return bitmap.getWidth();
    }

    public int getHeight() {
        return bitmap.getHeight();
    }

    public PixmapFormat getFormat() {
        return format;
    }

    public void dispose() {
        bitmap.recycle();
    }
}
