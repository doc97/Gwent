package fi.riissanen.gwent.game.factions;

import fi.riissanen.gwent.game.GameSystem;
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
import fi.riissanen.gwent.game.states.GameStates;
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
    private boolean listenerStatus;
    
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
        faction.process(new CardPlayedEvent(null));
        faction.process(new StateChangeEvent(null, null));
        assertFalse(faction.isTriggered());
    }
    
    @Test
    public void testAbility() {
        GameSystem system = new GameSystem(null);
        EventSystem events = new EventSystem();
        events.addListener(StateChangeEvent.class, new AbilityEventListener());
        system.initialize(new Player(true), new Player(false));
        system.getStateSystem().setEventSystem(events);

        faction = new Scoiatael();
        assertNotNull(faction.getAbility());
        faction.getAbility().activate(system);
        
        events.update();
        assertTrue(listenerStatus);
    }
    
    
    public class AbilityEventListener implements EventListener {
        @Override
        public void process(Event event) {
            if (event instanceof StateChangeEvent) {
                GameState old = ((StateChangeEvent) event).getOldState();
                if (old instanceof ChooseStartingPlayerState) {
                    listenerStatus = true;
                }
            }
        }
    }
}
