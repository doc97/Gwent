package fi.riissanen.gwent.game.cards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Daniel
 */
public abstract class CardCollection {
    
    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        if (card != null) {
            cards.add(card);
        }
    }

    public void addCards(Collection<? extends Card> cards) {
        for (Card card : cards) {
            addCard(card);
        }
    }

    public Card getCard(int index) {
        if (index < 0 || index >= cards.size()) {
            throw new IndexOutOfBoundsException(
                    "Tried to get a card with index " + index + ", but the "
                    + "collection only has " + cards.size() + " cards.");
        }
        return cards.get(index);
    }

    public int getCardCount() {
        return cards.size();
    }
}
