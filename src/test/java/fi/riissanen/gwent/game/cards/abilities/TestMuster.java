package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.Deck;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestMuster {

    private Unit unit;
    private Muster ability;
    
    @Before
    public void before() {
        unit = new Unit("", "");
        unit.setUnitType(UnitType.MELEE);
        unit.setFriendlyStatus(true);
        unit.setBaseStrength(2);
        UnitCard card = new UnitCard(unit);
        unit.setCard(card);
        ability = new Muster(card);
    }
    @Test
    public void testGetUnit() {
        assertEquals(unit, ability.getUnit());
    }
    
    @Test
    public void testActivate() {
        Gwent game = new Gwent();
        game.initialize();
        Player friendly = new Player(game, true);
        Deck deck = new Deck();
        // 10 cards are drawn when game system in initialized
        for (int i = 0; i < 10 + 2; i++) {
            deck.addCard(new UnitCard(unit));
        }
        friendly.setDeck(deck);
        game.getGameSystem().initialize(friendly, new Player(game, false));
        unit.getCard().setOwner(friendly);
        game.getGameSystem().getBoard().addUnit(unit, UnitType.MELEE, true);
        ability.activate(game.getGameSystem());
        int count = game.getGameSystem().getBoard().getRow(true, UnitType.MELEE).getUnitCount();
        assertEquals(3, count);
        assertEquals(0, friendly.getDeckSize());
    }
}
