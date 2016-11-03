package fi.riissanen.gwent.game.cards;

/**
 *
 * @author Daniel
 */
public class Deck extends CardCollection {

    private static final int MIN_CARDS = 22;

    public boolean validate() {
        return getCardCount() >= MIN_CARDS;
    }
}
