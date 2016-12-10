package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.cards.Card;

/**
 * Fired when a card has been played.
 * @author Daniel
 */
public class CardPlayedEvent implements Event {

    private final Card card;
    private final int row;
    
    /**
     * Constructor.
     * @param card The card that has been played
     * @param row The row onto which it was played, may be -1 in case of a weather card
     */
    public CardPlayedEvent(Card card, int row) {
        this.card = card;
        this.row = row;
    }
    
    public Card getCard() {
        return card;
    }
    
    public int getRowIndex() {
        return row;
    }
}
