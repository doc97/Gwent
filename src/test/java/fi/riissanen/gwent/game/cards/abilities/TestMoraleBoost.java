package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestMoraleBoost {

    private static Unit unit;
    private static MoraleBoost ability;
    
    @BeforeClass
    public static void init() {
        unit = new Unit("", "");
        unit.setUnitType(UnitType.MELEE);
        unit.setFriendlyStatus(true);
        unit.setBaseStrength(2);
        ability = new MoraleBoost(new UnitCard(unit));
        unit.addAbility(ability);
    }
    
    @Test
    public void testGetUnit() {
        assertEquals(unit, ability.getUnit());
    }
    
    @Test
    public void testActivate() {
        GameSystem system = new GameSystem(null);
        Player friendly = new Player(true);
        Player enemy = new Player(false);
        system.initialize(friendly, enemy);
        system.getBoard().addUnit(unit, UnitType.MELEE, true);
        system.getBoard().addUnit(unit, UnitType.MELEE, true);
        ability.activate(system);
        int strength = system.getBoard().getRow(true, UnitType.MELEE).getStrength();
        assertEquals(6, strength);
    }
}
