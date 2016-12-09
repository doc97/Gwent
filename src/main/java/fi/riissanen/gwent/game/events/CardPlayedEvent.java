package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.combat.UnitType;

/**
 * Fired when a card has been played.
 * @author Daniel
 */
public class CardPlayedEvent implements Event {

    private final Card card;
    private final int row;
    
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
