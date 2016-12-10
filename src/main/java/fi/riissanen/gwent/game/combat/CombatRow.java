package fi.riissanen.gwent.game.combat;

import fi.riissanen.gwent.game.cards.abilities.Ability;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a combat row on the game board.
 *
 * @author Daniel
 * @see GameBoard
 */
public class CombatRow {

    private final List<Unit> units = new ArrayList<>();

    /**
     * Adds a unit to the row as long as it is not null.
     * @param unit The unit to add
     */
    public void addUnit(Unit unit) {
        if (unit != null) {
            units.add(unit);
        }
    }

    /**
     * Gets the combined strength of all units in the row.
     * @return The combined strength
     */
    public int getStrength() {
        int sum = 0;
        for (Unit unit : units) {
            sum += unit.getStrength();
        }
        return sum;
    }
    
    /**
     * Lists all units in the row with a certain ability.
     * @param clazz The class of the ability
     * @return The list of units
     */
    public List<Unit> getUnitsWithAbility(Class<? extends Ability> clazz) {
        List<Unit> abilityUnits = new ArrayList<>();
        for (Unit u : units) {
            if (u.hasAbility(clazz)) {
                abilityUnits.add(u);
            }
        }
        return abilityUnits;
    }

    public List<Unit> getUnits() {
        return units;
    }
    
    /**
     * Returns the unit count in the row.
     * @return The unit count
     */
    public int getUnitCount() {
        return units.size();
    }
    
    /**
     * Checks whether the row contains a unit.
     * @param unit The unit to check
     * @return True if the row contains the unit
     */
    public boolean hasUnit(Unit unit) {
        return units.contains(unit);
    }
}
