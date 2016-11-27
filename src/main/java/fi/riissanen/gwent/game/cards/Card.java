package fi.riissanen.gwent.game.cards;

import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import java.util.List;

/**
 * A interface for a card
 * @author Daniel
 */
public interface Card {
    public List<Ability> getAbilities();
    public void setOwner(Player player);
    public Player getOwner();
    public String getName();
}
