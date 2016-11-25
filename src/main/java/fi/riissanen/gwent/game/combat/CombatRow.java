package fi.riissanen.gwent.game.combat;

import fi.riissanen.gwent.game.cards.abilities.Ability;
import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a combat row on the game board
 *
 * @author Daniel
 * @see GameBoard
 */
public class CombatRow {

    private final List<Unit> units = new ArrayList<>();

    public void addUnit(Unit unit) {
        if (unit != null) {
            units.add(unit);
        }
    }

    public int getStrength() {
        int sum = 0;
        for (Unit unit : units) {
            sum += unit.getStrength();
        }
        return sum;
    }
    
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
    
    public int getUnitCount() {
        return units.size();
    }
    
    public boolean hasUnit(Unit unit) {
        return units.contains(unit);
    }
}
