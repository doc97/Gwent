package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.combat.Unit;

/**
 * A special {@link Ability} for units.
 * @author Daniel
 */
public interface UnitAbility {
    
    /**
     * Returns the unit of the ability.
     * @return The unit
     */
    public Unit getUnit();
}
