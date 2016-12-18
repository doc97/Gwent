package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;

/**
 * Weather card ability that clears all other weather card effects.
 * @author Daniel
 */
public class ClearWeather implements Ability {

    @Override
    public void activate(GameSystem system) {
        system.getBoard().clearWeather();
    }

    @Override
    public String getName() {
        return "Clear weather";
    }

    @Override
    public String getDescription() {
        return "Removes all weather effects";
    }
}
