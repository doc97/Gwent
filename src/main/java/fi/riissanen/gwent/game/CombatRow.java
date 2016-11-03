package fi.riissanen.gwent.game;

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

    public List<Unit> getUnits() {
        return units;
    }
    
    public int getUnitCount() {
        return units.size();
    }
}
