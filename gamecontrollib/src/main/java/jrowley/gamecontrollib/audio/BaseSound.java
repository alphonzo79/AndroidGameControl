package jrowley.gamecontrollib.audio;

import android.media.SoundPool;

/**
 * Created by jrowley on 11/2/15.
 */
public class BaseSound implements Sound {
    int soundId;
    SoundPool soundPool;

    public BaseSound(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }

    public void play(float volume) {
        soundPool.play(soundId, volume, volume, 0, 0, 1);
    }

    public void dispose() {
        soundPool.unload(soundId);
    }
}
