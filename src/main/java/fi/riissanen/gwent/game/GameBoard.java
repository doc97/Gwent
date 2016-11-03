package fi.riissanen.gwent.game;

/**
 * A class representing the game board
 *
 * @author Daniel
 */
public class GameBoard {

    private final CombatRow[] friendlyRows;
    private final CombatRow[] enemyRows;

    public GameBoard() {
        friendlyRows = new CombatRow[]{
            new CombatRow(), new CombatRow(), new CombatRow()
        };
        enemyRows = new CombatRow[]{
            new CombatRow(), new CombatRow(), new CombatRow()
        };
    }

    public void addUnit(Unit unit, UnitType type, boolean isFriendly) {
        if (unit == null) {
            return;
        }
        
        if (!unit.getTypes().contains(type)) {
            throw new IllegalArgumentException("Unit is not of unit type " + type);
        }
        
        CombatRow[] rows = getRows(isFriendly);
        int index = type.getIndex();
        rows[index].addUnit(unit);
    }

    public int getStrength(boolean friendly) {
        int sum = 0;
        for (CombatRow row : getRows(friendly)) {
            sum += row.getStrength();
        }
        return sum;
    }
    
    public int getUnitCount(boolean friendly) {
        int sum = 0;
        for (CombatRow row : getRows(friendly)) {
            sum += row.getUnitCount();
        }
        return sum;
    }
    
    public int getTotalUnitCount() {
        int friendlyCount = getUnitCount(true);
        int enemyCount = getUnitCount(false);
        return friendlyCount + enemyCount;
    }

    private CombatRow[] getRows(boolean friendly) {
        return friendly ? friendlyRows : enemyRows;
    }
}
