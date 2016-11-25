package fi.riissanen.gwent.game.cards;

import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import java.util.ArrayList;
import java.util.List;
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
        collection.addCard(new Card() {
            @Override
            public String getName() { return ""; }
            @Override
            public List<Ability> getAbilities() { return null; }
            @Override
            public Player getOwner() { return null; }
        });
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
    public void testAddCardsOne() {
        List<Card> cards = new ArrayList<>();
        cards.add(new Card() {
            @Override
            public String getName() { return ""; }
            @Override
            public List<Ability> getAbilities() { return null; }
            @Override
            public Player getOwner() { return null; }
        });
        collection.addCards(cards);
        assertEquals(collection.getCardCount(), 1);
    }
    
    public void testAddCardsNull() {
        collection.addCards(null);
        assertEquals(collection.getCardCount(), 0);
    }
    
    public void testAddCardsEmpty() {
        List<Card> cards = new ArrayList<>();
        collection.addCards(cards);
        assertEquals(collection.getCardCount(), 0);
    }
    
    @Test
    public void testGetCardInBounds() {
        collection.addCard(new Card() {
            @Override
            public String getName() { return ""; }
            @Override
            public List<Ability> getAbilities() { return null; }
            @Override
            public Player getOwner() { return null; }
        });
        Card card = collection.getCard(0);
        assertNotNull(card);
    }
    
    @Test
    public void testGetCardOutOfBounds() {
        Card card = collection.getCard(0);
        assertNull(card);
    }
}
