package fi.riissanen.gwent.game.cards.attributes;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.combat.Unit;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestSpy {

    private final Spy spy = new Spy();
    private Unit unit;
    
    @Before
    public void init() {
        unit = new Unit("", "");
        unit.addAbility(spy);
        unit.addAttribute(spy);
    }
    
    @Test
    public void testActivateAttribute() {
        boolean isFriendly = unit.isFriendly();
        unit.reloadAttributes();
        assertEquals(!isFriendly, unit.isFriendly());
    }
    
    @Test
    public void testActivateAbility() {
        GameSystem system = new GameSystem(null);
        system.initialize(new Player(true), new Player(false));
        unit.reloadAttributes();
        boolean isFriendly = unit.isFriendly();
        spy.activate(system);
        assertEquals(!isFriendly, unit.isFriendly());
    }
}
