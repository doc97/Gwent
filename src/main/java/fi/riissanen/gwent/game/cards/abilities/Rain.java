package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.combat.CombatRow;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;

/**
 * Weather card ability affecting siege units.
 * @author Daniel
 */
public class Rain implements Ability {

    @Override
    public void activate(GameSystem system) {
        CombatRow friendly = system.getBoard().getRow(true, UnitType.SIEGE);
        CombatRow enemy = system.getBoard().getRow(false, UnitType.SIEGE);
        for (Unit unit : friendly.getUnits()) {
            unit.setBaseStrength(1);
        }
        for (Unit unit : enemy.getUnits()) {
            unit.setBaseStrength(1);
        }
    }

    @Override
    public String getName() {
        return "Torrential Rain";
    }

    @Override
    public String getDescription() {
        return "Sets all Siege units to strength 1 for both players";
    }

}
