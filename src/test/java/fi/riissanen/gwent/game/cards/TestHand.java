package fi.riissanen.gwent.game.cards;

import fi.riissanen.gwent.game.cards.abilities.Ability;
import java.util.List;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestHand {
    private final Hand hand = new Hand();
    
    @Test
    public void testValidateEmpty() {
        assertFalse(hand.validateStartingHand());
    }
    
    @Test
    public void testValidateOK() {
        for (int i = 0; i < Hand.STARTING_HAND_CARD_COUNT; i++) {
            hand.addCard(new Card() {
                @Override
                public String getName() { return ""; }
                @Override
                public List<Ability> getAbilities() { return null; }
            });
        }
        assertTrue(hand.validateStartingHand());
    }
}
