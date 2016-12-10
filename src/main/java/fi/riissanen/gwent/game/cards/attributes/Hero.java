package fi.riissanen.gwent.game.cards.attributes;

import fi.riissanen.gwent.game.combat.Unit;

/**
 * Hero attribute.
 * @author Daniel
 */
public class Hero implements Attribute {
    
    @Override
    public void activate(Unit unit) {
        unit.setImmuneStatus(true);
    }
    
    @Override
    public String getName() {
        return "Hero";
    }
    
    @Override
    public String getDescription() {
        return "The card is immune to all abilities and special cards";
    }
}
