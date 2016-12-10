package fi.riissanen.gwent.game.cards;

import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import java.util.List;

/**
 * A interface for a card.
 * @author Daniel
 */
public interface Card {
    
    /**
     * Gets a list of the cards abilities.
     * @return The list
     */
    public List<Ability> getAbilities();
    
    /**
     * Sets the owner of the card.
     * @param player The owner
     */
    public void setOwner(Player player);
    
    /**
     * Gets the owner of the card.
     * @return The owner
     */
    public Player getOwner();
    
    /**
     * Gets the name of the card.
     * @return The name
     */
    public String getName();
}
