package fi.riissanen.gwent.game.combat;

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
        Unit unit = new Unit("", "");
        unit.setUnitType(UnitType.MELEE);
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
        Unit unit = new Unit("", "");
        unit.setUnitType(UnitType.MELEE);
        
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
        Unit unit = new Unit("", "");
        unit.setBaseStrength(assertedStrength);
        unit.setUnitTypes(types);
        board.addUnit(unit, UnitType.MELEE, true);
        int strength = board.getStrength(true);
        assertEquals(strength, assertedStrength);
    }
    
    @Test
    public void testAddTwoUnits() {
        int assertedStrength1 = 5;
        int assertedStrength2 = 3;
        Unit unit1 = new Unit("", "");
        Unit unit2 = new Unit("", "");
        unit1.setBaseStrength(assertedStrength1);
        unit2.setBaseStrength(assertedStrength2);
        unit1.setUnitType(UnitType.MELEE);
        unit2.setUnitType(UnitType.RANGED);
        board.addUnit(unit1, UnitType.MELEE, true);
        board.addUnit(unit2, UnitType.RANGED, true);
        int strength = board.getStrength(true);
        assertEquals(strength, assertedStrength1 + assertedStrength2);
    }
}
