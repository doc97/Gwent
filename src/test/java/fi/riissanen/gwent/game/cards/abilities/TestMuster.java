package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.Deck;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestMuster {

    private static Unit unit;
    private static Muster ability;
    
    @BeforeClass
    public static void init() {
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
        GameSystem system = new GameSystem(null);
        Player friendly = new Player(true);
        Deck deck = new Deck();
        // 10 cards are drawn when game system in initialized
        for (int i = 0; i < 10 + 2; i++) {
            deck.addCard(new UnitCard(unit));
        }
        friendly.setDeck(deck);
        system.initialize(friendly, new Player(false));
        unit.getCard().setOwner(friendly);
        system.getBoard().addUnit(unit, UnitType.MELEE, true);
        ability.activate(system);
        int count = system.getBoard().getRow(true, UnitType.MELEE).getUnitCount();
        assertEquals(3, count);
        assertEquals(0, friendly.getDeckSize());
    }
}
