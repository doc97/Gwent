package fi.riissanen.gwent.game.cards.factories;

import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.cards.abilities.ClearWeather;
import fi.riissanen.gwent.game.cards.abilities.Fog;
import fi.riissanen.gwent.game.cards.abilities.Frost;
import fi.riissanen.gwent.game.cards.abilities.Rain;
import fi.riissanen.gwent.game.cards.neutral.WeatherCard;

/**
 * A specialized {@link CardFactory} for creating {@link WeatherCard}'s.
 * @author Daniel
 */
public class WeatherCardFactory implements CardFactory {

    @Override
    public WeatherCard createCard(CardData data) {
        String abilityStr = data.abilities.split(",")[0];
        Ability ability;
        if (abilityStr.equals("Frost")) {
            ability = new Frost();
        } else if (abilityStr.equals("Fog")) {
            ability = new Fog();
        } else if (abilityStr.equals("Rain")) {
            ability = new Rain();
        } else if (abilityStr.equals("Clear")) {
            ability = new ClearWeather() ;
        } else {
            return null;
        }
        WeatherCard card = new WeatherCard(ability);
        return card;
    }
}
