package fi.riissanen.gwent.game.cards;

import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.cards.factories.CardData;
import fi.riissanen.gwent.game.cards.factories.CardFactory;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestDeck {
    
    private final Deck deck = new Deck();
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
        assertFalse(deck.validate());
    }
    
    @Test
    public void testValidateOK() {
        for (int i = 0; i < Deck.MIN_CARDS; i++) {
            deck.addCard(factory.createCard(new CardData()));
        }
        assertTrue(deck.validate());
    }
}
