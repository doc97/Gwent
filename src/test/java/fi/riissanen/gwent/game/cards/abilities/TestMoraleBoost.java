package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestMoraleBoost {

    private Unit unit;
    private MoraleBoost ability;
    
    @Before
    public void before() {
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
        Gwent game = new Gwent();
        game.initialize();
        Player friendly = new Player(game, true);
        Player enemy = new Player(game, false);
        game.getGameSystem().initialize(friendly, enemy);
        game.getGameSystem().getBoard().addUnit(unit, UnitType.MELEE, true);
        game.getGameSystem().getBoard().addUnit(unit, UnitType.MELEE, true);
        ability.activate(game.getGameSystem());
        int strength = game.getGameSystem().getBoard().getRow(true, UnitType.MELEE).getStrength();
        assertEquals(6, strength);
    }
}
