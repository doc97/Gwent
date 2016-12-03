package fi.riissanen.gwent.game.cards.attributes;

import fi.riissanen.gwent.game.combat.Unit;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestHero {

    private final Hero attrib = new Hero();
    
    @Test
    public void testActivateNotImmune() {
        Unit unit = new Unit("", "");
        unit.setImmuneStatus(false);
        unit.addAttribute(attrib);
        unit.reloadAttributes();
        assertTrue(unit.isImmune());
    }
    
    @Test
    public void testActivateImmune() {
        Unit unit = new Unit("", "");
        unit.setImmuneStatus(true);
        unit.addAttribute(attrib);
        unit.reloadAttributes();
        assertTrue(unit.isImmune());
    }
}
