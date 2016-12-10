package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.states.GameStates;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestMedic {

    private final Unit unit = new Unit("", "");
    private final Medic ability = new Medic(new UnitCard(unit));
    
    @Test
    public void testGetUnit() {
        assertEquals(unit, ability.getUnit());
    }
    
    @Test
    public void testActivate() {
        Gwent game = new Gwent();
        game.initialize();
        game.getGameSystem().initialize(new Player(game, true), new Player(game, false));
        ability.activate(game.getGameSystem());
        assertTrue(game.getGameSystem().getStateSystem().isCurrentState(GameStates.DISCARD_PILE_STATE));
    }
}
