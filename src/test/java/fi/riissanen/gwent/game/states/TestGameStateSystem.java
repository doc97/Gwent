package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.Player;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestGameStateSystem {

    private final Gwent game = new Gwent();
    
    @Before
    public void before() {
        game.initialize();
        game.getGameSystem().initialize(new Player(game, true), new Player(game, false));
        // Pop the two first states in GameSystem#initialize to have an empty stack
        game.getGameSystem().getStateSystem().pop();
        game.getGameSystem().getStateSystem().pop();
    }
    
    @Test
    public void testPush() {
        for (GameStates stateKey : game.getGameSystem().getStateSystem().getLoadedStates()) {
            game.getGameSystem().getStateSystem().push(stateKey);
            assertTrue(game.getGameSystem().getStateSystem().isCurrentState(stateKey));
        }
    }
    
    @Test
    public void testPop() {
        game.getGameSystem().getStateSystem().push(GameStates.STAGE_STATE);
        game.getGameSystem().getStateSystem().pop();
        assertFalse(game.getGameSystem().getStateSystem().isCurrentState(GameStates.STAGE_STATE));
    }
    
    @Test
    public void testPopEmpty() {
        // Test that no EmptyStackException is thrown
        game.getGameSystem().getStateSystem().pop();
    }
    
    @Test
    public void testPushDuplicate() {
        game.getGameSystem().getStateSystem().push(GameStates.STAGE_STATE);
        game.getGameSystem().getStateSystem().push(GameStates.STAGE_STATE);
        game.getGameSystem().getStateSystem().pop();
        assertFalse(game.getGameSystem().getStateSystem().isCurrentState(GameStates.STAGE_STATE));
    }
}
