package fi.riissanen.gwent.game.factions;

import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.events.EventListener;

/**
 * Represents an abstract faction.
 * @author Daniel
 */
public abstract class Faction implements EventListener {
    
    /**
     * Returns the faction ability.
     * @return The ability
     */
    public abstract Ability getAbility();
    
    /**
     * Returns whether the conditions for the ability have been met.
     * @return True if conditions have been met
     */
    public abstract boolean isTriggered();
}
