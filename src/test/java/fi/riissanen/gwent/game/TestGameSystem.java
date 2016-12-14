package fi.riissanen.gwent.game;

import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Daniel
 */
public class TestGameSystem {

    private final Gwent game = new Gwent();
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    @Before
    public void before() {
        game.initialize();
        game.getGameSystem().initialize(new Player(game, true), new Player(game, false));
    }
    
    @Test
    public void testPlayCardInvalidRow() {
        Unit unit = new Unit("", "");
        unit.setUnitType(UnitType.MELEE);
        unit.setBaseStrength(1);
        game.getGameSystem().stageCard(new UnitCard(unit));
        game.getGameSystem().getStateSystem().update();
        exception.expect(IllegalStateException.class);
        game.getGameSystem().playCard(-1);
    }
    
    @Test
    public void testPlayCardNotStaged() {
        game.getGameSystem().unstageCard();
        assertFalse(game.getGameSystem().playCard(0));
    }
    
    @Test
    public void testPlayCardStaged() {
        Unit unit = new Unit("", "");
        unit.setUnitType(UnitType.MELEE);
        unit.setBaseStrength(1);
        game.getGameSystem().stageCard(new UnitCard(unit));
        game.getGameSystem().getStateSystem().update();
        assertTrue(game.getGameSystem().playCard(UnitType.MELEE.getIndex()));
    }
}
