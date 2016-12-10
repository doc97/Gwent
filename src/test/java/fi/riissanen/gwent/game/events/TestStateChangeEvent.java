package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.engine.assets.AssetManager;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.states.GameState;
import fi.riissanen.gwent.game.states.NormalState;
import fi.riissanen.gwent.game.ui.GUI;
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
        Gwent game = new Gwent();
        GameState old = new NormalState(game);
        event = new StateChangeEvent(old, null);
        assertEquals(old, event.getOldState());
    }
    
    @Test
    public void testGetNewState() {
        Gwent game = new Gwent();
        game.initialize();
        GameState newState = new NormalState(game);
        event = new StateChangeEvent(null, newState);
        assertEquals(newState, event.getNewState());
    }
}
