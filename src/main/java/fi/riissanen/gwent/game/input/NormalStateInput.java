package fi.riissanen.gwent.game.input;

import fi.riissanen.gwent.engine.input.InputAdapter;
import fi.riissanen.gwent.game.Gwent;
import org.lwjgl.glfw.GLFW;

/**
 * Handles all general inputs in the normal state.
 * @author Daniel
 */
public class NormalStateInput extends InputAdapter {

    private final Gwent game;
    
    /**
     * Constructor.
     * @param game The game instance
     */
    public NormalStateInput(Gwent game) {
        this.game = game;
    }
    
    @Override
    public boolean keyPressed(int key) {
        if (key == GLFW.GLFW_KEY_SPACE) {
            game.getGameSystem().passRound();
        }
        return false;
    }
}
