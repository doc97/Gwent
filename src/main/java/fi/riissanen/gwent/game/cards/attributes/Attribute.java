package fi.riissanen.gwent.game.cards.attributes;

import fi.riissanen.gwent.game.combat.Unit;

/**
 * An attribute that can be activated
 * @author Daniel
 */
public interface Attribute {
    public void activate(Unit unit);
    public String getName();
    public String getDescription();
}
