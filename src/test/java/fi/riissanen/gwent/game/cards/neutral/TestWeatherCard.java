package fi.riissanen.gwent.game.cards.neutral;

import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.WeatherAbility;
import fi.riissanen.gwent.game.combat.UnitType;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestWeatherCard {

    private final WeatherCard card = new WeatherCard(new WeatherAbility("", "", UnitType.MELEE));
    
    @Test
    public void testGetAbilities() {
        assertEquals(1, card.getAbilities().size());
    }
    
    @Test
    public void testSetOwner() {
        Gwent game = new Gwent();
        game.initialize();
        Player player = new Player(game, true);
        card.setOwner(player);
        assertEquals(player, card.getOwner());
    }
    
    @Test
    public void testGetName() {
        assertEquals(card.getAbilities().get(0).getName(), card.getName());
    }
}
