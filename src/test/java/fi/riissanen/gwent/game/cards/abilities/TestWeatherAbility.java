package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestWeatherAbility {

    private final WeatherAbility ability = new WeatherAbility("", "", UnitType.MELEE);
    
    @Test
    public void testActivate() {
        Gwent game = new Gwent();
        game.initialize();
        game.getGameSystem().initialize(new Player(game, true), new Player(game, false));
        Unit fUnit = new Unit("", "");
        fUnit.setUnitType(UnitType.MELEE);
        fUnit.setBaseStrength(2);
        fUnit.setFriendlyStatus(true);
        Unit eUnit = new Unit("", "");
        eUnit.setUnitType(UnitType.MELEE);
        eUnit.setBaseStrength(3);
        eUnit.setFriendlyStatus(false);
        game.getGameSystem().getBoard().addUnit(fUnit, UnitType.MELEE, true);
        game.getGameSystem().getBoard().addUnit(eUnit, UnitType.MELEE, false);
        ability.activate(game.getGameSystem());
        assertEquals(1, game.getGameSystem().getBoard().getStrength(true));
        assertEquals(1, game.getGameSystem().getBoard().getStrength(false));
    }
}
