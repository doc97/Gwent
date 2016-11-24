package fi.riissanen.gwent.game.combat;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestUnit {

    private Unit unit;
    
    @Before
    public void init() {
        unit = new Unit("", "");
        unit.setUnitType(UnitType.MELEE);
    }
    
    @Test
    public void testSetStrength() {
        unit.setBaseStrength(2);
        unit.setEffectStrength(1);
        assertEquals(3, unit.getStrength());
    }
}
