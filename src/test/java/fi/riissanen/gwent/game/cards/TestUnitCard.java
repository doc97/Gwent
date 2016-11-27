package fi.riissanen.gwent.game.cards;

import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Medic;
import fi.riissanen.gwent.game.combat.Unit;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestUnitCard {

    private static UnitCard card;
    
    @BeforeClass
    public static void init() {
        Unit unit = new Unit("Alice", "Cool");
        card = new UnitCard(unit);
    }
    
    @Test
    public void testGetOwner() {
        Player player = new Player(null, true);
        card.setPlayer(player);
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
