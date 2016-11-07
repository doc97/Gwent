package fi.riissanen.gwent.game.combat;

import java.util.ArrayList;
import java.util.List;
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
        List<UnitType> types = new ArrayList<>();
        types.add(UnitType.MELEE);
        unit = new Unit(types, 1);
    }
    
    @Test
    public void testSetStrength() {
        unit.setStrength(2);
        assertEquals(2, unit.getStrength());
    }
}
