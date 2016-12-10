package fi.riissanen.gwent.game.cards;

/**
 * A hand containing cards.
 * @author Daniel
 */
public class Hand extends CardCollection {

    public static final int STARTING_HAND_CARD_COUNT = 10;

    /**
     * Checks whether the hand is valid.
     * @return True if all checks pass
     */
    public boolean validateStartingHand() {
        return getCardCount() == STARTING_HAND_CARD_COUNT;
    }
}
