package fi.riissanen.gwent.game.factions;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Gwent;
import static fi.riissanen.gwent.game.MatchManager.MAX_LIVES;
import fi.riissanen.gwent.game.MatchManager.Result;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import fi.riissanen.gwent.game.events.RoundEndEvent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestNilfgaardianEmpire {

    private NilfgaardianEmpire faction;
    
    @Test
    public void testIsTriggeredTrue() {
        faction = new NilfgaardianEmpire(null);
        faction.process(new RoundEndEvent(Result.DRAW));
        assertTrue(faction.isTriggered());
    }
    
    @Test
    public void testIsTriggeredFalse() {
        faction = new NilfgaardianEmpire(null);
        faction.process(new RoundEndEvent(Result.NONE));
        faction.process(new RoundEndEvent(Result.WIN));
        faction.process(new RoundEndEvent(Result.LOSS));
        assertFalse(faction.isTriggered());
    }
    
    @Test
    public void testAbility() {
        Gwent game = new Gwent();
        game.initialize();
        Player friendly = new Player(game, true);
        game.getGameSystem().initialize(friendly, new Player(game, false));
        Unit unit1 = new Unit("", "");
        Unit unit2 = new Unit("", "");
        unit1.setUnitType(UnitType.MELEE);
        unit2.setUnitType(UnitType.MELEE);
        game.getGameSystem().getBoard().addUnit(unit1, UnitType.MELEE, true);
        game.getGameSystem().getBoard().addUnit(unit2, UnitType.MELEE, true);
        faction = new NilfgaardianEmpire(friendly);
        assertNotNull(faction.getAbility());
        faction.getAbility().activate(game.getGameSystem());
        game.getGameSystem().getMatchManager().finishRound();
        assertEquals(MAX_LIVES - 1, game.getGameSystem().getMatchManager().getEnemyLives());
        assertEquals(MAX_LIVES, game.getGameSystem().getMatchManager().getFriendlyLives());
    }
}
