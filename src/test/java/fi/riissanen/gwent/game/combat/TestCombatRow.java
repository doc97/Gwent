package fi.riissanen.gwent.game.combat;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestCombatRow {

    private final CombatRow row = new CombatRow();
    
    @Test
    public void testAddUnitNull() {
        row.addUnit(null);
        int count = row.getUnitCount();
        assertEquals(count, 0);
    }
    
    @Test
    public void testAddUnitNotNull() {
        List<UnitType> types = new ArrayList<>();
        types.add(UnitType.MELEE);
        row.addUnit(new Unit("", ""));
        int count = row.getUnitCount();
        assertEquals(count, 1);
    }
}
