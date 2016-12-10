package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;

/**
 * An ability that can be activated.
 * 
 * More info for each ability can be read from their description
 * @author Daniel
 */
public interface Ability {
    
    /**
     * Called when the ability is activated.
     * @param system The game system
     */
    public void activate(GameSystem system);
    
    /**
     * Returns the name of the ability.
     * @return The name
     */
    public String getName();
    
    /**
     * Returns the description of the ability.
     * @return The description
     */
    public String getDescription();
}
