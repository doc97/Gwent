package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.combat.CombatRow;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;

/**
 * The ability of the weather card.
 * @author Daniel
 */
public class WeatherAbility implements Ability {

    private final String name;
    private final String description;
    private final UnitType row;
    
    /**
     * Constructor.
     * @param name The name of the ability
     * @param description The description of the ability
     * @param row The row on which it was played
     */
    public WeatherAbility(String name, String description, UnitType row) {
        this.name = name;
        this.description = description;
        this.row = row;
    }
    
    @Override
    public void activate(GameSystem system) {
        CombatRow friendly = system.getBoard().getRow(true, row);
        CombatRow enemy = system.getBoard().getRow(false, row);
        for (Unit unit : friendly.getUnits()) {
            unit.setBaseStrength(1);
        }
        for (Unit unit : enemy.getUnits()) {
            unit.setBaseStrength(1);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
