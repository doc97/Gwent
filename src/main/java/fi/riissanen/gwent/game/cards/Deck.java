package fi.riissanen.gwent.game.cards;

/**
 * A deck of cards which can be validated.
 * @author Daniel
 */
public class Deck extends CardCollection {

    public static final int MIN_CARDS = 22;

    /**
     * Checks that the deck is valid.
     * @return True if the checks pass
     */
    public boolean validate() {
        return getCardCount() >= MIN_CARDS;
    }
}
