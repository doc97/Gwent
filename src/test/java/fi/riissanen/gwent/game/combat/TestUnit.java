package fi.riissanen.gwent.game.combat;

import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.cards.abilities.Medic;
import fi.riissanen.gwent.game.cards.attributes.Attribute;
import fi.riissanen.gwent.game.cards.attributes.Hero;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestUnit {

    private Unit unit;
    
    @Before
    public void init() {
        unit = new Unit("Alice", "Awesome unit");
        unit.setUnitType(UnitType.MELEE);
    }
    
    @Test
    public void testReloadAttributes() {
        Attribute attribute = new Attribute() {
            @Override
            public void activate(Unit unit) {
                unit.setUnitType(UnitType.RANGED);
            }

            @Override
            public String getName() {
                return "";
            }

            @Override
            public String getDescription() {
                return "";
            }
        };
        unit.addAttribute(attribute);
        unit.reloadAttributes();
        assertEquals(UnitType.RANGED, unit.getTypes().toArray()[0]);
    }
    
    @Test
    public void testSetStrength() {
        unit.setBaseStrength(2);
        unit.setEffectStrength(1);
        assertEquals(3, unit.getStrength());
    }
        
    @Test
    public void testSetBaseStrength() {
        unit.setBaseStrength(3);
        assertEquals(3, unit.getBaseStrength());
    }
    
    @Test
    public void testSetEffectStrength() {
        unit.setEffectStrength(3);
        assertEquals(3, unit.getEffectStrength());
    }
    
    @Test
    public void testSetImmuneTrue() {
        unit.setEffectStrength(2);
        unit.setImmuneStatus(true);
        assertTrue(unit.isImmune());
        assertEquals(unit.getEffectStrength(), 0);
    }
    
    @Test
    public void testSetImmuneFalse() {
        unit.setEffectStrength(2);
        unit.setImmuneStatus(false);
        assertFalse(unit.isImmune());
        assertEquals(unit.getEffectStrength(), 2);
    }
    
    @Test
    public void testHasAbility() {
        unit.addAbility(new Medic(null));
        assertTrue(unit.hasAbility(Medic.class));
    }
    
    @Test
    public void testHasNotAbility() {
        assertFalse(unit.hasAbility(Medic.class));
    }
    
    @Test
    public void testHasAttribute() {
        unit.addAttribute(new Hero());
        assertTrue(unit.hasAttribute(Hero.class));
    }
    
    @Test
    public void testHasNotAttribute() {
        assertFalse(unit.hasAttribute(Hero.class));
    }
    
    @Test
    public void testGetTypeIndices() {
        List<UnitType> types = new ArrayList<>();
        types.add(UnitType.MELEE);
        types.add(UnitType.RANGED);
        unit.setUnitTypes(types);
        int[] typeIndices = unit.getTypeIndices();
        assertEquals(typeIndices[0], UnitType.MELEE.getIndex());
        assertEquals(typeIndices[1], UnitType.RANGED.getIndex());
    }
    
    @Test
    public void testGetTypeIndicesEmpty() {
        Unit testUnit = new Unit("", "");
        int[] typeIndices = testUnit.getTypeIndices();
        assertEquals(typeIndices.length, 0);
    }
    
    @Test
    public void testSetCard() {
        UnitCard card = new UnitCard(unit);
        unit.setCard(card);
        assertEquals(card, unit.getCard());
    }
    
    @Test
    public void testSetFriendlyStatus() {
        unit.setFriendlyStatus(true);
        assertTrue(unit.isFriendly());
        unit.setFriendlyStatus(false);
        assertFalse(unit.isFriendly());
    }
    
    @Test
    public void testGetName() {
        assertEquals("Alice", unit.getName());
    }
    
    @Test
    public void testGetDescription() {
        assertEquals("Awesome unit", unit.getDescription());
    }
}
