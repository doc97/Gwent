package fi.riissanen.gwent.game.cards;

import fi.riissanen.gwent.game.cards.abilities.Ability;
import java.util.List;
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
            deck.addCard(new Card() {
                @Override
                public String getName() { return ""; }
                @Override
                public List<Ability> getAbilities() { return null; }
            });
        }
        assertTrue(deck.validate());
    }
}
