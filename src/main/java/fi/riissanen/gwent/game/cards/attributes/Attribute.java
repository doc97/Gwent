package fi.riissanen.gwent.game.cards.attributes;

import fi.riissanen.gwent.game.combat.Unit;

/**
 * An attribute that can be activated.
 * @author Daniel
 */
public interface Attribute {
    
    /**
     * Called when the attribute is activated.
     * @param unit The unit to affect
     */
    public void activate(Unit unit);
    
    /**
     * The name of the attribute.
     * @return The name
     */
    public String getName();
    
    /**
     * The description of the attribute.
     * @return The description
     */
    public String getDescription();
}
