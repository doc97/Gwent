package fi.riissanen.gwent.game.cards;

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
            hand.addCard(new Card() {});
        }
        assertTrue(hand.validateStartingHand());
    }
}
