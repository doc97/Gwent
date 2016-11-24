package fi.riissanen.gwent.game.cards.attributes;

import fi.riissanen.gwent.game.combat.Unit;

/**
 *
 * @author Daniel
 */
public interface Attribute {
    public void activate(Unit unit);
    public String getName();
    public String getDescription();
}
