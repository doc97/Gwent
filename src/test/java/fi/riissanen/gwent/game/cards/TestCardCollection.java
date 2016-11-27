package fi.riissanen.gwent.game.cards;

import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.cards.factories.CardData;
import fi.riissanen.gwent.game.cards.factories.CardFactory;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestCardCollection {
    
    private final CardCollection collection = new CardCollection(){};
    private final CardFactory factory = new CardFactory() {
        @Override
        public Card createCard(CardData data) {
            return new Card() {
                private Player owner;
                @Override
                public List<Ability> getAbilities() { return new ArrayList<>(); }

                @Override
                public void setOwner(Player owner) {
                    this.owner = owner;
                }

                @Override
                public Player getOwner() {
                    return owner;
                }

                @Override
                public String getName() {
                    return "Alice";
                }
            };
        }
    };
    
    @Test
    public void testAddCardNotNull() {
        Card card = factory.createCard(new CardData());
        collection.addCard(card);
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
        cards.add(factory.createCard(new CardData()));
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
        collection.addCard(factory.createCard(new CardData()));
        Card card = collection.getCard(0);
        assertNotNull(card);
    }
    
    @Test
    public void testGetCardOutOfBounds() {
        Card card = collection.getCard(0);
        assertNull(card);
    }
    
    @Test
    public void testRemoveCardExists() {
        Card card = factory.createCard(new CardData());
        collection.addCard(card);
        assertTrue(collection.removeCard(card));
        assertEquals(collection.getCardCount(), 0);
    }
    
    @Test
    public void testRemoveCardNotExists() {
        Card card = factory.createCard(new CardData());
        collection.addCard(factory.createCard(new CardData()));
        assertFalse(collection.removeCard(card));
        assertEquals(collection.getCardCount(), 1);
    }
    
    @Test
    public void testContainsCard() {
        Card card = factory.createCard(new CardData());
        Card exist = factory.createCard(new CardData());
        collection.addCard(exist);
        assertTrue(collection.containsCard(exist));
        assertFalse(collection.containsCard(card));
    }
}
