package fi.riissanen.gwent.game.combat;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.Logger.LogLevel;
import fi.riissanen.gwent.game.cards.neutral.WeatherCard;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class representing the game board.
 *
 * @author Daniel
 */
public class GameBoard {

    private final CombatRow[] friendlyRows;
    private final CombatRow[] enemyRows;
    private final List<WeatherCard> weatherPile;
    private final List<Unit> savedUnits;

    /**
     * Constructor, initializes arrays and lists.
     */
    public GameBoard() {
        friendlyRows = new CombatRow[]{
            new CombatRow(), new CombatRow(), new CombatRow()
        };
        enemyRows = new CombatRow[]{
            new CombatRow(), new CombatRow(), new CombatRow()
        };
        weatherPile = new ArrayList<>();
        savedUnits = new ArrayList<>();
    }

    /**
     * Adds a unit to a combat row.
     * @param unit The unit to add
     * @param type The row to which the unit is to be added
     * @param isFriendly True if the unit's owner is friendly
     */
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
    
    /**
     * Adds a weather card to the weather card pile.
     * @param weather The card
     */
    public void addWeatherCard(WeatherCard weather) {
        if (weather != null) {
            weatherPile.add(weather);
        }
    }
    
    /**
     * Empties the weather card pile to the players' discard piles.
     */
    public void clearWeather() {
        for (WeatherCard card : weatherPile) {
            card.getOwner().discardCard(card);
        }
        weatherPile.clear();
    }
    
    /**
     * Empties the combat rows to the players' discard piles.
     * 
     * <p>
     * The units that are saved are not discarded.
     * @see GameBoard#saveUnit(Unit)
     */
    public void clearRows() {
        for (CombatRow row : getRows(true)) {
            for (Iterator<Unit> it = row.getUnits().iterator(); it.hasNext();) {
                Unit unit = it.next();
                if (!savedUnits.contains(unit)) {
                    unit.getOwner().discardCard(unit.getCard());
                    it.remove();
                }
            }
        }
        savedUnits.clear();
    }
    
    /**
     * Save a unit from the next combat row clear.
     * @param u The unit to save
     */
    public void saveUnit(Unit u) {
        savedUnits.add(u);
    }
    
    /**
     * Prints the state of the game board to the engine log.
     */
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

    /**
     * Returns the strength of a row.
     * @param row The row
     * @param friendly True if the row belongs to the friendly player
     * @return The row strength
     */
    public int getRowStrength(UnitType row, boolean friendly) {
        return getRow(friendly, row).getStrength();
    }
    
    /**
     * Returns the combined strength of all combat rows belonging to a player.
     * @param friendly True if the rows belong to the friendly player
     * @return The combined strength
     */
    public int getStrength(boolean friendly) {
        int sum = 0;
        for (CombatRow row : getRows(friendly)) {
            sum += row.getStrength();
        }
        return sum;
    }
    
    /**
     * Gets the combined unit count of a player.
     * @param friendly True if the rows belong to the friendly player
     * @return The combined unit count
     */
    public int getUnitCount(boolean friendly) {
        int sum = 0;
        for (CombatRow row : getRows(friendly)) {
            sum += row.getUnitCount();
        }
        return sum;
    }
    
    /**
     * Gets the combined unit count of both players.
     * @return The combined unit count
     */
    public int getTotalUnitCount() {
        int friendlyCount = getUnitCount(true);
        int enemyCount = getUnitCount(false);
        return friendlyCount + enemyCount;
    }

    /**
     * Gets the size of the weather card pile.
     * @return The card count
     */
    public int getWeatherCardCount() {
        return weatherPile.size();
    }
    
    /**
     * Gets the number of units that will be saved the next combat row clear.
     * @return The unit count
     */
    public int getSavedUnitCount() {
        return savedUnits.size();
    }
    
    /**
     * Get the rows of a player.
     * @param friendly True if the rows belong to the friendly player
     * @return The combat rows
     */
    public CombatRow[] getRows(boolean friendly) {
        return friendly ? friendlyRows : enemyRows;
    }
    
    /**
     * Gets one row of a player.
     * @param friendly True if the rows belong to the friendly player
     * @param row The row type
     * @return The combat row
     */
    public CombatRow getRow(boolean friendly, UnitType row) {
        return friendly ? friendlyRows[row.getIndex()] : enemyRows[row.getIndex()];
    }
    
    /**
     * Gets the row that a unit is on.
     * @param unit The unit to check with
     * @return The combat row, or null if the unit is not in any combat row
     */
    public CombatRow getRow(Unit unit) {
        for (CombatRow row : getRows(unit.isFriendly())) {
            if (row.hasUnit(unit)) {
                return row;
            }
        }
        return null;
    }
    
    /**
     * Gets the index of a combat row.
     * @param row The row to get the index for
     * @return The index, or -1 if the rows have not been initialised properly
     */
    public int getRowIndex(CombatRow row) {
        CombatRow[] fRows = getRows(true);
        CombatRow[] eRows = getRows(false);
        for (int i = 0; i < fRows.length; i++) {
            if (fRows[i].equals(row) || eRows[i].equals(row)) {
                return i;
            }
        }
        return -1;
    }
}
