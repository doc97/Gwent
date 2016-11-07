package fi.riissanen.gwent.game.cards;

import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestUnitCard {
    
    private UnitCard card;
    
    @Test
    public void testGetUnitNull() {
        card = new UnitCard(null);
        assertNull(card.getUnit());
    }
    
    @Test
    public void testGetUnitNotNull() {
        List<UnitType> types = new ArrayList<>();
        types.add(UnitType.MELEE);
        card = new UnitCard(new Unit(types, 0));
        Assert.assertNotNull(card.getUnit());
    }
}