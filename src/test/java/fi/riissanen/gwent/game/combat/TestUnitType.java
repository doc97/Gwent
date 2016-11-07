package fi.riissanen.gwent.game.combat;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestUnitType {
    
    @Test
    public void testGetIndex() {
        assertEquals(UnitType.MELEE.getIndex(), 0);
        assertEquals(UnitType.RANGED.getIndex(), 1);
        assertEquals(UnitType.SIEGE.getIndex(), 2);
    }
}
