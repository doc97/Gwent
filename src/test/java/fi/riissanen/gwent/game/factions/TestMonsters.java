package fi.riissanen.gwent.game.factions;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import fi.riissanen.gwent.game.events.CardPlayedEvent;
import fi.riissanen.gwent.game.events.MatchStartEvent;
import fi.riissanen.gwent.game.events.RoundEndEvent;
import fi.riissanen.gwent.game.events.StateChangeEvent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestMonsters {

    private Monsters faction;
    
    @Test
    public void testIsTriggeredTrue() {
        faction = new Monsters(null);
        faction.process(new RoundEndEvent(null));
        assertTrue(faction.isTriggered());
    }
    
    @Test
    public void testIsTriggeredFalse() {
        faction = new Monsters(null);
        faction.process(new CardPlayedEvent(null, 0));
        faction.process(new MatchStartEvent());
        faction.process(new StateChangeEvent(null, null));
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
        faction = new Monsters(friendly);
        assertNotNull(faction.getAbility());
        faction.getAbility().activate(game.getGameSystem());
        assertEquals(1, game.getGameSystem().getBoard().getSavedUnitCount());
    }
}
