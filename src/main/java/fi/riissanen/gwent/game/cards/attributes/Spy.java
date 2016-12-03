package fi.riissanen.gwent.game.cards.attributes;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.combat.Unit;

/**
 * Spy attribute
 * @author Daniel
 */
public class Spy implements Attribute, Ability {

    private Unit unit;
    
    @Override
    public void activate(Unit unit) {
        this.unit = unit;
        unit.setFriendlyStatus(!unit.isFriendly());
    }
    
    @Override
    public void activate(GameSystem system) {
        unit.setFriendlyStatus(!unit.isFriendly());
    }

    @Override
    public String getName() {
        return "Spy";
    }

    @Override
    public String getDescription() {
        return "The card is placed on the enemies battlefield, you draw 2 cards";
    }
}
