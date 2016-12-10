package fi.riissanen.gwent.game.cards.factories;

import fi.riissanen.gwent.game.cards.Card;

/**
 * A card factory (Factory pattern).
 * @author Daniel
 */
public interface CardFactory {
    
    /**
     * Creates the card.
     * @param data The data to create the card from
     * @return The created card
     */
    public Card createCard(CardData data);
}
