package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.cards.Card;

/**
 * Fires when a player draws a card.
 * @author Daniel
 */
public class DrawCardEvent implements Event {

    private final Card card;
    private final boolean friendly;
    
    /**
     * Constructor.
     * @param card The card that was drawn
     * @param friendly If it was the friendly player who drew
     */
    public DrawCardEvent(Card card, boolean friendly) {
        this.card = card;
        this.friendly = friendly;
    }
    
    public Card getCard() {
        return card;
    }
    
    /**
     * Returns whether it was the friendly player who drew the card.
     * @return True if friendly, false if enemy
     */
    public boolean isFriendly() {
        return friendly;
    }
}
