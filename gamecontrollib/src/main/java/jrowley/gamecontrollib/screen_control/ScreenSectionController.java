package jrowley.gamecontrollib.screen_control;

import jrowley.gamecontrollib.game_control.GameController;

/**
 * Created by jrowley on 11/5/15.
 */
public abstract class ScreenSectionController {
    protected int sectionLeft;
    protected int sectionTop;

    public ScreenSectionController(int sectionLeft, int sectionTop) {
        this.sectionLeft = sectionLeft;
        this.sectionTop = sectionTop;
    }
    
    public abstract void update(float deltaTime, GameController gameController);
    public abstract void present(float deltaTime, GameController gameController);
    public abstract void pause(GameController gameController);
    public abstract void resume(GameController gameController);
    public abstract void dispose(GameController gameController);
}
