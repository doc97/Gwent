package fi.riissanen.gwent.game.cards.factories;

import fi.riissanen.gwent.game.cards.Card;

/**
 * Used to create cards with {@code CardFactory}.
 * @author Daniel
 */
public class CardCreator {

    private final UnitCardFactory unitCards = new UnitCardFactory();
    private final WeatherCardFactory weatherCards = new WeatherCardFactory();
    
    /**
     * Creates a card based on {@code CardData}.
     * @param data The card data
     * @return The created card
     */
    public Card createCard(CardData data) {
        if (data.type.equals("Unit")) {
            return unitCards.createCard(data);
        } else if (data.type.equals("Weather")) {
            return weatherCards.createCard(data);
        }

        return null;
    }
}
