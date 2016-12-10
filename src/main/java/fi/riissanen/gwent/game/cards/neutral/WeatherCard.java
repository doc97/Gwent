package fi.riissanen.gwent.game.cards.neutral;

import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import java.util.ArrayList;
import java.util.List;

/**
 * A special card that also is neutral, meaning it is accessible to all factions.
 * @author Daniel
 */
public class WeatherCard implements Card {

    private Player owner;
    private final List<Ability> abilities = new ArrayList<>();
    
    /**
     * Creates a weather card with an ability.
     * 
     * <p>
     * The ability cannot be null, or the constructor throws an
     * {@code IllegalArgumentException}
     * @param ability The weather card ability
     */
    public WeatherCard(Ability ability) {
        if (ability == null) {
            throw new IllegalArgumentException("Weather card ability must not be null");
        }
        abilities.add(ability);
    }
    
    @Override
    public void setOwner(Player owner) {
        this.owner = owner;
    }
    
    @Override
    public List<Ability> getAbilities() {
        return abilities;
    }
    
    @Override
    public Player getOwner() {
        return owner;
    }
    
    @Override
    public String getName() {
        return abilities.get(0).getName();
    }
}
