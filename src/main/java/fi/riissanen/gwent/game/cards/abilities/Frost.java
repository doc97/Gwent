package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.combat.CombatRow;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;

/**
 *
 * @author Daniel
 */
public class Frost implements Ability {

    @Override
    public void activate(GameSystem system) {
        CombatRow friendly = system.getBoard().getRow(true, UnitType.MELEE);
        CombatRow enemy = system.getBoard().getRow(false, UnitType.MELEE);
        for (Unit unit : friendly.getUnits()) {
            unit.setBaseStrength(1);
        }
        for (Unit unit : enemy.getUnits()) {
            unit.setBaseStrength(1);
        }
    }

    @Override
    public String getName() {
        return "Biting Frost";
    }

    @Override
    public String getDescription() {
        return "Sets all melee units to strength 1 for both players";
    }
}
