package fi.riissanen.gwent.game;

import fi.riissanen.gwent.game.cards.Hand;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.UnitType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestGameSystem {

    private static GameSystem gameSys;
    
    @BeforeClass
    public static void init() {
        gameSys = new GameSystem();
        gameSys.initialize();
    }
    
    @Test
    public void testInitialization() {
        assertTrue(gameSys.initialize());
    }
    
    @Test
    public void testPlayCardFriendlyUnitCard() {
        // Play a card
        Hand hand = gameSys.getHand();
        UnitCard card = (UnitCard) hand.getCard(0);
        UnitType row = card.getUnit().getTypes().iterator().next();
        gameSys.playCard(hand, card, row, true);
        int friendlyCount = gameSys.getBoard().getUnitCount(true);
        int enemyCount = gameSys.getBoard().getUnitCount(false);
        assertEquals(1, friendlyCount);
        assertEquals(0, enemyCount);
    }
    
    @Test
    public void testPlayCardEnemyUnitCard() {
        // Play a card
        Hand hand = gameSys.getHand();
        UnitCard card = (UnitCard) hand.getCard(0);
        UnitType row = card.getUnit().getTypes().iterator().next();
        gameSys.playCard(hand, card, row, false);
        int friendlyCount = gameSys.getBoard().getUnitCount(true);
        int enemyCount = gameSys.getBoard().getUnitCount(false);
        assertEquals(0, friendlyCount);
        assertEquals(1, enemyCount);
    }
}
