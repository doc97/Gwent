package fi.riissanen.gwent.game.factions;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.Deck;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import fi.riissanen.gwent.game.events.CardPlayedEvent;
import fi.riissanen.gwent.game.events.MatchStartEvent;
import fi.riissanen.gwent.game.events.RoundEndEvent;
import fi.riissanen.gwent.game.events.StateChangeEvent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestNorthernKingdoms {
    
    private NorthernKingdoms faction;
    
    @Test
    public void testIsTriggeredTrue() {
        faction = new NorthernKingdoms(null);
        faction.process(new RoundEndEvent(null));
        assertTrue(faction.isTriggered());
    }
    
    @Test
    public void testIsTriggeredFalse() {
        faction = new NorthernKingdoms(null);
        faction.process(new CardPlayedEvent(null));
        faction.process(new MatchStartEvent());
        faction.process(new StateChangeEvent(null, null));
        assertFalse(faction.isTriggered());
    }
    
    @Test
    public void testAbility() {
        Deck deck = new Deck();
        deck.addCard(new UnitCard(new Unit("", "")));
        Player friendly = new Player(true);
        friendly.setDeck(deck);
        faction = new NorthernKingdoms(friendly);
        assertNotNull(faction.getAbility());
        faction.getAbility().activate(null);
        assertEquals(1, friendly.getHandSize());
        assertEquals(0, friendly.getDeckSize());
    }
}
