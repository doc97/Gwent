package fi.riissanen.gwent.game.cards.attributes;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.Player;
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
        Gwent game = new Gwent();
        game.initialize();
        game.getGameSystem().initialize(new Player(game, true), new Player(game, false));
        unit.reloadAttributes();
        boolean isFriendly = unit.isFriendly();
        spy.activate(game.getGameSystem());
        assertEquals(!isFriendly, unit.isFriendly());
    }
}
