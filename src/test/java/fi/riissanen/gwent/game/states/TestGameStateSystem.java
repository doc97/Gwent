package fi.riissanen.gwent.game.states;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestGameStateSystem {

    private final GameStateSystem system = new GameStateSystem();
    
    @Before
    public void before() {
        system.initialize(null);
    }
    
    @Test
    public void testPush() {
        for (GameStates stateKey : system.getLoadedStates()) {
            system.push(stateKey);
            assertTrue(system.isCurrentState(stateKey));
        }
    }
    
    @Test
    public void testPop() {
        system.push(GameStates.STAGE_STATE);
        system.pop();
        assertFalse(system.isCurrentState(GameStates.STAGE_STATE));
    }
    
    @Test
    public void testPopEmpty() {
        // Test that no EmptyStackException is thrown
        system.pop();
    }
    
    @Test
    public void testPushDuplicate() {
        system.push(GameStates.STAGE_STATE);
        system.push(GameStates.STAGE_STATE);
        system.pop();
        assertFalse(system.isCurrentState(GameStates.STAGE_STATE));
    }
}
