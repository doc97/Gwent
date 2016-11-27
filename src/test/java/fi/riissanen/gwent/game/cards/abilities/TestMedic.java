package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
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
        GameSystem system = new GameSystem(null);
        system.initialize(new Player(true), new Player(false));
        ability.activate(system);
        assertTrue(system.getStateSystem().isCurrentState(GameStates.DISCARD_PILE_STATE));
    }
}
