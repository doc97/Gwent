package fi.riissanen.gwent.game.cards;

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
public class TestUnitCard {

    private UnitCard card;
    
    @Before
    public void before() {
        Unit unit = new Unit("Alice", "Cool");
        card = new UnitCard(unit);
    }
    
    @Test
    public void testGetOwner() {
        Gwent game = new Gwent();
        game.initialize();
        Player player = new Player(game, true);
        card.setOwner(player);
        assertEquals(player, card.getOwner());
    }

    @Test
    public void testGetName() {
        assertEquals("Alice", card.getName());
    }
    
    @Test
    public void testGetAbilities() {
        assertEquals(0, card.getAbilities().size());
    }
}
