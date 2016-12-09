package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.cards.Card;

/**
 * Fires when a player draws a card.
 * @author Daniel
 */
public class DrawCardEvent implements Event {

    private final Card card;
    private final boolean friendly;
    
    public DrawCardEvent(Card card, boolean friendly) {
        this.card = card;
        this.friendly = friendly;
    }
    
    public Card getCard() {
        return card;
    }
    
    public boolean wasFriendly() {
        return friendly;
    }
}
