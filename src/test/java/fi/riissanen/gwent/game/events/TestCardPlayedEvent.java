package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.cards.factories.CardData;
import fi.riissanen.gwent.game.cards.factories.CardFactory;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestCardPlayedEvent {

    private CardPlayedEvent event;
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
    public void testGetCard() {
        Card card = factory.createCard(null);
        event = new CardPlayedEvent(card, 0);
        assertEquals(card, event.getCard());
    }
}
