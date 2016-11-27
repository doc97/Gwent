package fi.riissanen.gwent.game.cards;

import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.cards.factories.CardData;
import fi.riissanen.gwent.game.cards.factories.CardFactory;
import fi.riissanen.gwent.game.cards.factories.UnitCardFactory;
import java.util.ArrayList;
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
    public void testValidateEmpty() {
        assertFalse(hand.validateStartingHand());
    }
    
    @Test
    public void testValidateOK() {
        for (int i = 0; i < Hand.STARTING_HAND_CARD_COUNT; i++) {
            hand.addCard(factory.createCard(new CardData()));
        }
        assertTrue(hand.validateStartingHand());
    }
}
