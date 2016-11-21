package fi.riissanen.gwent.game.combat;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.Logger.LogLevel;

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
    
    public void status() {
        Engine.INSTANCE.log.write(LogLevel.INFO, "Board state");
        
        String rowName = "";
        int strength = 0;
        int rowCount = UnitType.values().length;
        for (int i = 0; i < rowCount * 2; i++) {
            boolean friendly = (i >= rowCount);
            UnitType row = UnitType.values()[i % rowCount];
            rowName = row.toString();
            strength = getRowStrength(row, friendly);
            Engine.INSTANCE.log.write(LogLevel.INFO, rowName + ": " + strength);
            
            if (i == rowCount - 1) {
                Engine.INSTANCE.log.write(LogLevel.INFO, "---");
            }
        }
    }

    public int getRowStrength(UnitType row, boolean friendly) {
        return getRow(friendly, row).getStrength();
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
    
    public CombatRow getRow(boolean friendly, UnitType row) {
        return friendly ? friendlyRows[row.getIndex()] : enemyRows[row.getIndex()];
    }
}
