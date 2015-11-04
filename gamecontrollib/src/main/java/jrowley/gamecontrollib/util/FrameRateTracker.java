package jrowley.gamecontrollib.util;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import jrowley.gamecontrollib.graphics.Graphics;

/**
 * Created by jrowley on 11/4/15.
 */
public class FrameRateTracker {
    private float[] deltaTimes;
    private int index;

    private int frameRate;
    private float deltaAccumulator;
    private int nonZeros;

    private final String SPACE_FPS = " fps";
    private final int LEFT = 50;
    private final int TOP = 50;
    private final int TEXT_COLOR = Color.WHITE;
    private final int FONT_SIZE = 12;

    public FrameRateTracker() {
        deltaTimes = new float[100];
        index = 0;
    }

    public void update(float portionOfSecond) {
        deltaTimes[index++] = portionOfSecond;
        if(index >= deltaTimes.length) {
            index = 0;
        }
    }

    private int getFrameRate() {
        frameRate = 0;
        deltaAccumulator = 0;
        nonZeros = 0;

        for(int i = 0; i < deltaTimes.length; i++) {
            if(deltaTimes[i] > 0) {
                deltaAccumulator += deltaTimes[i];
                nonZeros++;
            }
        }

        if(deltaAccumulator > 0 && nonZeros > 0) {
            frameRate = (int) (nonZeros / deltaAccumulator);
        }

        return frameRate;
    }

    public void writeFrameRate(Graphics graphics) {
        graphics.writeText(String.valueOf(getFrameRate()) + SPACE_FPS, LEFT, TOP, TEXT_COLOR, FONT_SIZE, Typeface.SANS_SERIF, Paint.Align.LEFT);
    }
}
