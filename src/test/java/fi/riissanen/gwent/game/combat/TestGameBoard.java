package fi.riissanen.gwent.game.combat;

import fi.riissanen.gwent.game.combat.GameBoard;
import fi.riissanen.gwent.game.combat.UnitType;
import fi.riissanen.gwent.game.combat.Unit;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Daniel
 */
public class TestGameBoard {

    private final GameBoard board = new GameBoard();
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testAddUnitInvalidUnitType() {
        List<UnitType> types = new ArrayList<>();
        types.add(UnitType.MELEE);
        Unit unit = new Unit(types, 1);
        exception.expect(IllegalArgumentException.class);
        board.addUnit(unit, UnitType.RANGED, true);
    }
    
    @Test
    public void testAddUnitNullUnit() {
        board.addUnit(null, UnitType.MELEE, true);
        int count = board.getTotalUnitCount();
        assertEquals(count, 0);
    }
    
    @Test
    public void testAddUnitNullUnitType() {
        List<UnitType> types = new ArrayList<>();
        types.add(UnitType.MELEE);
        Unit unit = new Unit(types, 1);
        
        try {
            board.addUnit(unit, null, true);
        } catch (NullPointerException npe) {
            fail("No handling of null unit type");
        } catch (Throwable t) {
            
        }
        assertTrue(true);
    }
    
    @Test
    public void testAddOneUnit() {
        List<UnitType> types = new ArrayList<>();
        types.add(UnitType.MELEE);
        types.add(UnitType.RANGED);
        int assertedStrength = 5;
        Unit unit = new Unit(types, assertedStrength);
        board.addUnit(unit, UnitType.MELEE, true);
        int strength = board.getStrength(true);
        assertEquals(strength, assertedStrength);
    }
    
    @Test
    public void testAddTwoUnits() {
        List<UnitType> types = new ArrayList<>();
        types.add(UnitType.MELEE);
        types.add(UnitType.RANGED);
        int assertedStrength1 = 5;
        int assertedStrength2 = 3;
        Unit unit1 = new Unit(types, assertedStrength1);
        Unit unit2 = new Unit(types, assertedStrength2);
        board.addUnit(unit1, types.get(0), true);
        board.addUnit(unit2, types.get(0), true);
        int strength = board.getStrength(true);
        assertEquals(strength, assertedStrength1 + assertedStrength2);
    }
}
