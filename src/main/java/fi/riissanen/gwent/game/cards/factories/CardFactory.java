package fi.riissanen.gwent.game.cards.factories;

import fi.riissanen.gwent.game.cards.Card;

/**
 *
 * @author Daniel
 */
public interface CardFactory {
    public Card createCard(CardData data);
}
