package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.cards.Card;

/**
 * Fired when a card has been played
 * @author Daniel
 */
public class CardPlayedEvent implements Event {

    private final Card card;
    
    public CardPlayedEvent(Card card) {
        this.card = card;
    }
    
    public Card getCard() {
        return card;
    }
}
