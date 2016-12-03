package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.states.GameState;
import fi.riissanen.gwent.game.states.HandState;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestStateChangeEvent {

    private StateChangeEvent event;
    
    @Test
    public void testGetOldState() {
        GameState old = new HandState();
        event = new StateChangeEvent(old, null);
        assertEquals(old, event.getOldState());
    }
    
    @Test
    public void testGetNewState() {
        GameState newState = new HandState();
        event = new StateChangeEvent(null, newState);
        assertEquals(newState, event.getNewState());
    }
}
