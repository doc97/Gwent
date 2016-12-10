package fi.riissanen.gwent.game.cards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An abstract data structure for {@link Card}s.
 * @author Daniel
 */
public abstract class CardCollection {
    
    private final List<Card> cards = new ArrayList<>();

    /**
     * Adds a card to the collection, unless null.
     * @param card The card to add
     */
    public void addCard(Card card) {
        if (card != null) {
            cards.add(card);
        }
    }

    /**
     * Adds a collection of cards.
     * @param cards The collection
     */
    public void addCards(Collection<? extends Card> cards) {
        for (Card card : cards) {
            addCard(card);
        }
    }
    
    /**
     * Clears the card collection.
     */
    public void clearCards() {
        cards.clear();
    }
    
    /**
     * Removes a card from the collection.
     * @param card The card to remove
     * @return The success of the removal
     */
    public boolean removeCard(Card card) {
        return cards.remove(card);
    }
    
    /**
     * Checks whether a collection contains the card.
     * @param card The card to check
     * @return True if the collection contains the card
     */
    public boolean containsCard(Card card) {
        return cards.contains(card);
    }

    /**
     * Gets a card based on it's index.
     * @param index The card index
     * @return The card or null if the index is out of bounds
     */
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
