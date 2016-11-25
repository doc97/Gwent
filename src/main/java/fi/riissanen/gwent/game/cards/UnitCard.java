package fi.riissanen.gwent.game.cards;

import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.combat.Unit;
import java.util.List;

/**
 * A {@link Card} that contains a unit
 *
 * @author Daniel
 */
public class UnitCard implements Card {
    
    
    private final Unit unit;

    public UnitCard(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }
    
    @Override
    public List<Ability> getAbilities() {
        return unit.getAbilities();
    }
    
    @Override
    public String getName() {
        return unit.getName();
    }
    
    @Override
    public String toString() {
        return getName() + " - <" + unit.getStrength() + ">";
    }
}
