package fi.riissanen.gwent.game;

import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.Deck;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Daniel
 */
public class TestPlayer {

    private final Player player = new Player(true);
    private final Card card1 = new UnitCard(new Unit("Alice", ""));
    private final Card card2 = new UnitCard(new Unit("Bob", ""));
    private final Card card3 = new UnitCard(new Unit("Bob", ""));
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    @Before
    public void before() {
        List<Card> cards = new ArrayList<>();
        cards.add(card1);
        cards.add(card2);
        cards.add(card3);
        Deck deck = new Deck();
        deck.addCards(cards);
        player.setDeck(deck);
    }
    
    @Test
    public void testSetDeck() {
        assertEquals(3, player.getDeckSize());
        assertEquals(card1, player.getDeckCard(0));
        assertEquals(card2, player.getDeckCard(1));
        assertEquals(card3, player.getDeckCard(2));
    }
    
    @Test
    public void testSetDeckClear() {
        List<Card> cards = new ArrayList<>();
        Deck deck = new Deck();
        deck.addCards(cards);
        player.setDeck(deck);
        player.setDeck(deck);
        assertEquals(deck.getCardCount(), player.getDeckSize());
    }
    
    @Test
    public void testRemoveCardFromHand() {
        player.drawCards(3);
        player.removeCardFromHand(card1);
        assertEquals(2, player.getHandSize());
    }
    
    @Test
    public void testRemoveCardFromHandNotExist() {
        player.drawCards(2);
        player.removeCardFromHand(new UnitCard(null));
        assertEquals(2, player.getHandSize());
        player.removeCardFromHand(null);
        assertEquals(2, player.getHandSize());
    }
    
    @Test
    public void testRemoveCardFromDeck() {
        player.removeCardFromDeck(card1);
        assertEquals(2, player.getDeckSize());
    }
    
    @Test
    public void testRemoveCardFromDeckNotExist() {
        player.removeCardFromDeck(new UnitCard(null));
        assertEquals(3, player.getDeckSize());
        player.removeCardFromDeck(null);
        assertEquals(3, player.getDeckSize());
    }
    
    @Test
    public void testDiscardCard() {
        player.discardCard(card1);
        player.discardCard(card2);
        assertEquals(2, player.getDiscardPileSize());
    }
    
    @Test
    public void testPopDiscardCardInBounds() {
        player.discardCard(card1);
        Card popped = player.popDiscardCard(0);
        assertEquals(card1, popped);
        assertEquals(0, player.getDiscardPileSize());
    }
    
    @Test
    public void testPopDiscardCardOutOfBounds() {
        player.discardCard(card1);
        Card popped = player.popDiscardCard(1);
        assertNull(popped);
        assertEquals(1, player.getDiscardPileSize());
    }
    
    @Test
    public void testDrawCard() {
        player.drawCard();
        assertEquals(1, player.getHandSize());
    }
    
    @Test
    public void testDrawCardEmptyDeck() {
        player.drawCards(3);
        player.drawCard();
        assertEquals(3, player.getHandSize());
        assertEquals(0, player.getDeckSize());
    }
    
    @Test
    public void testDrawCards() {
        player.drawCards(2);
        assertEquals(2, player.getHandSize());
        assertEquals(1, player.getDeckSize());
    }
    
    @Test
    public void testGetDeckCardsByName() {
        int aliceCounts = player.getDeckCardsByName("Alice").size();
        int bobCounts = player.getDeckCardsByName("Bob").size();
        int charlieCounts = player.getDeckCardsByName("Charlie").size();
        assertEquals(1, aliceCounts);
        assertEquals(2, bobCounts);
        assertEquals(0, charlieCounts);
    }
    
    @Test
    public void testRedrawCardExist() {
        player.drawCard();
        Card card = player.getHandCard(0);
        player.redrawCard(card);
        assertEquals(1, player.getHandSize());
        assertEquals(2, player.getDeckSize());
        assertNotEquals(card, player.getHandCard(0));
    }
    
    @Test
    public void testRedrawCardNotExist() {
        Card card = new UnitCard(null);
        exception.expect(IllegalArgumentException.class);
        player.redrawCard(card);
        assertEquals(0, player.getHandSize());
        assertEquals(3, player.getDeckSize());
    }
}
