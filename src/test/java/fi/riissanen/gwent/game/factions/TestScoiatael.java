package fi.riissanen.gwent.game.factions;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.MatchManager.Result;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.events.CardPlayedEvent;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.events.EventListener;
import fi.riissanen.gwent.game.events.EventSystem;
import fi.riissanen.gwent.game.events.MatchStartEvent;
import fi.riissanen.gwent.game.events.RoundEndEvent;
import fi.riissanen.gwent.game.events.StateChangeEvent;
import fi.riissanen.gwent.game.states.ChooseStartingPlayerState;
import fi.riissanen.gwent.game.states.GameState;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestScoiatael {

    private Scoiatael faction;
    
    @Test
    public void testIsTriggeredTrue() {
        faction = new Scoiatael();
        faction.process(new MatchStartEvent());
        assertTrue(faction.isTriggered());
    }
    
    @Test
    public void testIsTriggeredFalse() {
        faction = new Scoiatael();
        faction.process(new RoundEndEvent(Result.LOSS));
        faction.process(new CardPlayedEvent(null, 0));
        faction.process(new StateChangeEvent(null, null));
        assertFalse(faction.isTriggered());
    }
    
    @Test
    public void testAbility() {
        Gwent game = new Gwent();
        game.initialize();
        AbilityEventListener listener = new AbilityEventListener();
        game.getEventSystem().addListener(StateChangeEvent.class, listener);
        game.getGameSystem().initialize(new Player(game, true), new Player(game, false));

        faction = new Scoiatael();
        assertNotNull(faction.getAbility());
        faction.getAbility().activate(game.getGameSystem());
        game.getGameSystem().getStateSystem().update();
        
        game.getEventSystem().update();
        assertTrue(listener.getStatus());
    }
    
    
    public class AbilityEventListener implements EventListener {
        
        private boolean status = false;
        
        @Override
        public void process(Event event) {
            if (event instanceof StateChangeEvent) {
                GameState old = ((StateChangeEvent) event).getOldState();
                if (old instanceof ChooseStartingPlayerState) {
                    status = true;
                }
            }
        }
        
        public boolean getStatus() {
            return status;
        }
    }
}
