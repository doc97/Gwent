package fi.riissanen.gwent.game;

import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.CardCollection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestCardCollection {
    
    private final CardCollection collection = new CardCollection(){};
    
    @Test
    public void testAddCardNotNull() {
        collection.addCard(new Card() {});
        int count = collection.getCardCount();
        assertEquals(count, 1);
    }
    
    @Test
    public void testAddCardNull() {
        collection.addCard(null);
        int count = collection.getCardCount();
        assertEquals(count, 0);
    }
    
    @Test
    public void testGetCardInBounds() {
        collection.addCard(new Card() {});
        Card card = collection.getCard(0);
        assertNotNull(card);
    }
    
    @Test
    public void testGetCardOutOfBounds() {
        Card card = collection.getCard(0);
        assertNull(card);
    }
}
