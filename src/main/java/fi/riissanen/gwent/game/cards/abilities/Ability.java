package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;

/**
 * An ability that can be activated
 * 
 * More info for each ability can be read from their description
 * @author Daniel
 */
public interface Ability {
    public void activate(GameSystem system);
    public String getName();
    public String getDescription();
}
