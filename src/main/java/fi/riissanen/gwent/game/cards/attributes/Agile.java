package fi.riissanen.gwent.game.cards.attributes;

import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import java.util.ArrayList;
import java.util.List;

/**
 * Agile attribute
 * @author Daniel
 */
public class Agile implements Attribute {

    @Override
    public void activate(Unit unit) {
        List<UnitType> types = new ArrayList<>();
        types.add(UnitType.MELEE);
        types.add(UnitType.RANGED);
        unit.setUnitTypes(types);
    }
    
    @Override
    public String getName() {
        return "Agile";
    }
    
    @Override
    public String getDescription() {
        return "The card can be placed in both the melee or ranged combat row";
    }
}
