package fi.riissanen.gwent.game.combat;

import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Frost;
import fi.riissanen.gwent.game.cards.neutral.WeatherCard;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Daniel
 */
public class TestGameBoard {

    private final GameBoard board = new GameBoard();
    
    @Rule
    public final ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testAddUnitInvalidUnitType() {
        Unit unit = new Unit("", "");
        unit.setUnitType(UnitType.MELEE);
        exception.expect(IllegalArgumentException.class);
        board.addUnit(unit, UnitType.RANGED, true);
    }
    
    @Test
    public void testAddUnitNullUnit() {
        board.addUnit(null, UnitType.MELEE, true);
        int count = board.getTotalUnitCount();
        assertEquals(0, count);
    }
    
    @Test
    public void testAddUnitNullUnitType() {
        Unit unit = new Unit("", "");
        unit.setUnitType(UnitType.MELEE);
        
        try {
            board.addUnit(unit, null, true);
        } catch (NullPointerException npe) {
            fail("No handling of null unit type");
        } catch (Throwable t) {
            
        }
        assertTrue(true);
    }
    
    @Test
    public void testAddOneUnit() {
        List<UnitType> types = new ArrayList<>();
        types.add(UnitType.MELEE);
        types.add(UnitType.RANGED);
        int assertedStrength = 5;
        Unit unit = new Unit("", "");
        unit.setBaseStrength(assertedStrength);
        unit.setUnitTypes(types);
        board.addUnit(unit, UnitType.MELEE, true);
        int strength = board.getStrength(true);
        assertEquals(assertedStrength, strength);
    }
    
    @Test
    public void testAddTwoUnits() {
        int assertedStrength1 = 5;
        int assertedStrength2 = 3;
        Unit unit1 = new Unit("", "");
        Unit unit2 = new Unit("", "");
        unit1.setBaseStrength(assertedStrength1);
        unit2.setBaseStrength(assertedStrength2);
        unit1.setUnitType(UnitType.MELEE);
        unit2.setUnitType(UnitType.RANGED);
        board.addUnit(unit1, UnitType.MELEE, true);
        board.addUnit(unit2, UnitType.RANGED, true);
        int strength = board.getStrength(true);
        assertEquals(assertedStrength1 + assertedStrength2, strength);
    }

    @Test
    public void testAddWeatherCard() {
        Frost ability = new Frost();
        board.addWeatherCard(new WeatherCard(ability));
        assertEquals(1, board.getWeatherCardCount());
    }
    
    @Test
    public void testAddWeatherCardNull() {
        board.addWeatherCard(null);
        assertEquals(0, board.getWeatherCardCount());
    }
    
    @Test
    public void testClearWeather() {
        Gwent game = new Gwent();
        game.initialize();
        Player player = new Player(game, true);
        WeatherCard card = new WeatherCard(new Frost());
        card.setOwner(player);
        board.addWeatherCard(card);
        board.clearWeather();
        assertEquals(0, board.getWeatherCardCount());
        assertEquals(1, player.getDiscardPileSize());
    }
    
    @Test
    public void testClearRowsNoSaved() {
        Unit u1 = new Unit("", "");
        u1.setUnitType(UnitType.MELEE);
        Unit u2 = new Unit("", "");
        u2.setUnitType(UnitType.RANGED);
        board.addUnit(u1, UnitType.MELEE, true);
        board.addUnit(u2, UnitType.RANGED, true);
        board.clearRows();
        assertEquals(0, board.getTotalUnitCount());
    }
    
    @Test
    public void testClearRowsSaved() {
        Unit u1 = new Unit("", "");
        u1.setUnitType(UnitType.MELEE);
        Unit u2 = new Unit("", "");
        u2.setUnitType(UnitType.RANGED);
        Unit u3 = new Unit("", "");
        u3.setUnitType(UnitType.SIEGE);

        board.addUnit(u1, UnitType.MELEE, true);
        board.addUnit(u2, UnitType.RANGED, false);
        board.addUnit(u3, UnitType.SIEGE, true);
        
        board.saveUnit(u1);
        board.saveUnit(u2);
        board.clearRows();
        assertEquals(2, board.getTotalUnitCount());
        assertEquals(0, board.getSavedUnitCount());
    }
    
    @Test
    public void testRowStrength() {
        Unit u = new Unit("", "");
        List<UnitType> types = new ArrayList<>();
        types.add(UnitType.MELEE);
        types.add(UnitType.RANGED);
        types.add(UnitType.SIEGE);
        u.setUnitTypes(types);
        u.setBaseStrength(2);
        Unit u2 = new Unit("", "");
        u2.setUnitType(UnitType.RANGED);
        u2.setBaseStrength(3);
        board.addUnit(u, UnitType.MELEE, true);
        board.addUnit(u, UnitType.RANGED, true);
        board.addUnit(u2, UnitType.RANGED, true);
        board.addUnit(u, UnitType.SIEGE, false);
        assertEquals(u.getStrength(), board.getRowStrength(UnitType.MELEE, true));
        assertEquals(u.getStrength() + u2.getStrength(),
                board.getRowStrength(UnitType.RANGED, true));
        assertEquals(u.getStrength(), board.getRowStrength(UnitType.SIEGE, false));
    }
    
    @Test
    public void testGetUnitCount() {
        Unit u1 = new Unit("", "");
        u1.setUnitType(UnitType.MELEE);
        Unit u2 = new Unit("", "");
        u2.setUnitType(UnitType.RANGED);
        Unit u3 = new Unit("", "");
        u3.setUnitType(UnitType.RANGED);
        board.addUnit(u1, UnitType.MELEE, true);
        board.addUnit(u2, UnitType.RANGED, true);
        board.addUnit(u3, UnitType.RANGED, false);
        assertEquals(2, board.getUnitCount(true));
        assertEquals(1, board.getUnitCount(false));
    }
    
    @Test
    public void testGetTotalUnitCount() {
        Unit u1 = new Unit("", "");
        u1.setUnitType(UnitType.MELEE);
        Unit u2 = new Unit("", "");
        u2.setUnitType(UnitType.RANGED);
        Unit u3 = new Unit("", "");
        u3.setUnitType(UnitType.RANGED);
        board.addUnit(u1, UnitType.MELEE, true);
        board.addUnit(u2, UnitType.RANGED, true);
        board.addUnit(u3, UnitType.RANGED, false);
        assertEquals(3, board.getTotalUnitCount());
    }
    
    @Test
    public void testGetRowByUnit() {
        Unit u = new Unit("", "");
        u.setUnitType(UnitType.MELEE);
        u.setFriendlyStatus(true);
        board.addUnit(u, UnitType.MELEE, u.isFriendly());
        assertNotNull(board.getRow(u));
    }
    
    @Test
    public void testGetRowByUnitNull() {
        Unit u = new Unit("", "");
        assertNull(board.getRow(u));
    }
    
    @Test
    public void testGetRowIndex() {
        CombatRow friendly = board.getRow(true, UnitType.RANGED);
        CombatRow enemy = board.getRow(false, UnitType.RANGED);
        assertEquals(UnitType.RANGED.getIndex(), board.getRowIndex(friendly));
        assertEquals(UnitType.RANGED.getIndex(), board.getRowIndex(enemy));
        assertEquals(-1, board.getRowIndex(null));
    }
}
