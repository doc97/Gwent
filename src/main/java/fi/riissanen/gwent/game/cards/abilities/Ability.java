package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;

/**
 *
 * @author Daniel
 */
public interface Ability {
    public void activate(GameSystem system);
    public String getName();
    public String getDescription();
}
