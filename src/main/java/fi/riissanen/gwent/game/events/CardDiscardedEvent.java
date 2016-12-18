package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.cards.Card;

/**
 * Fired when a card has been discarded.
 * @author Daniel
 */
public class CardDiscardedEvent implements Event {
    
    private final Card card;
    
    /**
     * Constructor.
     * @param card The card that was discarded
     */
    public CardDiscardedEvent(Card card) {
        this.card = card;
    }
    
    public Card getCard() {
        return card;
    }
}
