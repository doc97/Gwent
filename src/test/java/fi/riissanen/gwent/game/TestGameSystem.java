package fi.riissanen.gwent.game;

import fi.riissanen.gwent.game.cards.Deck;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Daniel
 */
public class TestGameSystem {

    private static GameSystem gameSys;
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    @BeforeClass
    public static void init() {
        gameSys = new GameSystem(null);
        gameSys.initialize(new Player(true), new Player(false));
    }
    
    @Test
    public void testPlayCardInvalidRow() {
        Unit unit = new Unit("", "");
        unit.setUnitType(UnitType.MELEE);
        unit.setBaseStrength(1);
        gameSys.stageCard(new UnitCard(unit));
        exception.expect(IllegalStateException.class);
        gameSys.playCard(-1);
    }
    
    @Test
    public void testPlayCardNotStaged() {
        gameSys.unstageCard();
        assertFalse(gameSys.playCard(0));
    }
    
    @Test
    public void testPlayCardStaged() {
        Unit unit = new Unit("", "");
        unit.setUnitType(UnitType.MELEE);
        unit.setBaseStrength(1);
        gameSys.stageCard(new UnitCard(unit));
        assertTrue(gameSys.playCard(UnitType.MELEE.getIndex()));
    }
}
