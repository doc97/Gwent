package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.combat.CombatRow;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;

/**
 * Weather card ability affecting ranged units.
 * @author Daniel
 */
public class Fog implements Ability {

    @Override
    public void activate(GameSystem system) {
        CombatRow friendly = system.getBoard().getRow(true, UnitType.RANGED);
        CombatRow enemy = system.getBoard().getRow(false, UnitType.RANGED);
        for (Unit unit : friendly.getUnits()) {
            unit.setBaseStrength(1);
        }
        for (Unit unit : enemy.getUnits()) {
            unit.setBaseStrength(1);
        }
    }

    @Override
    public String getName() {
        return "Impenetrable fog";
    }

    @Override
    public String getDescription() {
        return "Sets all Ranged combat units to strength 1 for both players";
    }
}
