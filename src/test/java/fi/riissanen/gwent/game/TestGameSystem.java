package fi.riissanen.gwent.game;

import fi.riissanen.gwent.game.cards.Hand;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.UnitType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestGameSystem {

    private static GameSystem gameSys;
    
    @BeforeClass
    public static void init() {
        gameSys = new GameSystem();
        gameSys.initialize();
    }
    
    @Test
    public void testInitialization() {
        assertTrue(gameSys.initialize());
    }
}
