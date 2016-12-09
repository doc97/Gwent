package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.cards.Card;

/**
 * Fired when a card has been staged.
 * @author Daniel
 */
public class CardStageEvent implements Event {

    private final Card card;
    private final boolean staged;
    
    public CardStageEvent(Card card, boolean staged) {
        this.card = card;
        this.staged = staged;
    }
    
    public Card getCard() {
        return card;
    }
    
    public boolean cardIsStaged() {
        return staged;
    }
}
