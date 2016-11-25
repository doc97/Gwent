package fi.riissanen.gwent.game.cards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An abstract data structure for {@link Card}s
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
    
    public boolean removeCard(Card card) {
        return cards.remove(card);
    }
    
    public boolean containsCard(Card card) {
        return cards.contains(card);
    }

    public Card getCard(int index) {
        if (index < 0 || index >= cards.size()) {
            return null;
        }
        return cards.get(index);
    }

    public int getCardCount() {
        return cards.size();
    }
}
