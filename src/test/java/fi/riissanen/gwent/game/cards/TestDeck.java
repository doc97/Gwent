package fi.riissanen.gwent.game.cards;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestDeck {
    
    private final Deck deck = new Deck();
    
    @Test
    public void testValidateEmpty() {
        assertFalse(deck.validate());
    }
    
    @Test
    public void testValidateOK() {
        for (int i = 0; i < Deck.MIN_CARDS; i++) {
            deck.addCard(new Card() {});
        }
        assertTrue(deck.validate());
    }
}
