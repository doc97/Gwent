package fi.riissanen.gwent.game.cards.attributes;

import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestAgile {

    private final Agile attrib = new Agile();
    
    @Test
    public void testActivate() {
        Unit unit = new Unit("", "");
        unit.setUnitType(UnitType.SIEGE);
        unit.addAttribute(attrib);
        unit.reloadAttributes();
        assertEquals(2, unit.getTypes().size());
        assertTrue(unit.getTypes().contains(UnitType.MELEE));
        assertTrue(unit.getTypes().contains(UnitType.RANGED));
    }
}
