package fi.riissanen.gwent.game.combat;

import fi.riissanen.gwent.game.cards.abilities.Medic;
import fi.riissanen.gwent.game.cards.abilities.Muster;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
        row.addUnit(new Unit("", ""));
        int count = row.getUnitCount();
        assertEquals(count, 1);
    }
    
    @Test
    public void testGetUnitsWithAbility() {
        Unit u1 = new Unit("Alice", "");
        u1.addAbility(new Medic(null));
        Unit u2 = new Unit("Bob", "");
        u2.addAbility(new Muster(null));
        Unit u3 = new Unit("Calvin", "");
        u3.addAbility(new Medic(null));
        u3.addAbility(new Muster(null));
        row.addUnit(u1);
        row.addUnit(u2);
        row.addUnit(u3);
        List<Unit> medics = row.getUnitsWithAbility(Medic.class);
        assertTrue(medics.contains(u1));
        assertTrue(medics.contains(u3));
        assertFalse(medics.contains(u2));
    }
    
    @Test
    public void testGetUnitsEmpty() {
        assertTrue(row.getUnits().isEmpty());
    }
    
    @Test
    public void testGetUnitsNonEmpty() {
        Unit alice = new Unit("Alice", "");
        row.addUnit(alice);
        assertTrue(row.getUnits().contains(alice));
    }
    
    @Test
    public void testHasUnitTrue() {
        Unit alice = new Unit("Alice", "");
        row.addUnit(alice);
        assertTrue(row.hasUnit(alice));
    }
    
    @Test
    public void testHasUnitFalse() {
        Unit alice = new Unit("Alice", "");
        assertFalse(row.hasUnit(alice));
    }
}
