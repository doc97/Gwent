package fi.riissanen.gwent.game.cards.attributes;

import fi.riissanen.gwent.game.combat.Unit;

/**
 * Spy attribute
 * @author Daniel
 */
public class Spy implements Attribute {

    @Override
    public void activate(Unit unit) {
        unit.setFriendlyStatus(false);
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
