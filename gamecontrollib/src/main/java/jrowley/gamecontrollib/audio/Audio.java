package jrowley.gamecontrollib.audio;

/**
 * Created by jrowley on 11/2/15.
 */
public interface Audio {
    public Music newMusic(String filename);
    public Sound newSound(String filename);
}
