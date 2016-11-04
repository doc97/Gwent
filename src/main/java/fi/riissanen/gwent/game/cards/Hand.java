package fi.riissanen.gwent.game.cards;

/**
 *
 * @author Daniel
 */
public class Hand extends CardCollection {

    public static final int STARTING_HAND_CARD_COUNT = 10;

    public boolean validateStartingHand() {
        return getCardCount() == STARTING_HAND_CARD_COUNT;
    }
}
