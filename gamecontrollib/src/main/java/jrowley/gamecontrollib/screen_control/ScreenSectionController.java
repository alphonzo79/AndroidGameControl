package jrowley.gamecontrollib.screen_control;

import jrowley.gamecontrollib.game_control.GameController;

/**
 * Created by jrowley on 11/5/15.
 */
public abstract class ScreenSectionController {
    protected GameController gameController;

    protected int sectionLeft;
    protected int sectionTop;
    protected int sectionWidth;
    protected int sectionHeight;

    public ScreenSectionController(int sectionLeft, int sectionTop, int sectionWidth, int sectionHeight, GameController gameController) {
        this.gameController = gameController;
        this.sectionLeft = sectionLeft;
        this.sectionTop = sectionTop;
        this.sectionWidth = sectionWidth;
        this.sectionHeight = sectionHeight;
    }

    public abstract void update(float deltaTime);
    public abstract void present(float deltaTime);
    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();
}
