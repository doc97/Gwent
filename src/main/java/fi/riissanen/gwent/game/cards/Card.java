package fi.riissanen.gwent.game.cards;

import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import java.util.List;

/**
 *
 * @author Daniel
 */
public interface Card {
    public List<Ability> getAbilities();
    public Player getOwner();
    public String getName();
}
