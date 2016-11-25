package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */

public class TightBond implements Ability, UnitAbility {

    private final Card card;
    
    public TightBond(Card card) {
        this.card = card;
    }
    
    @Override
    public void activate(GameSystem system) {
        List<Unit> units = system.getBoard().getRow(getUnit()).getUnits();
        List<Unit> affectedUnits = new ArrayList<>();
        for (Unit rowUnit : units) {
            if (rowUnit.getName().equals(getUnit().getName())) {
                affectedUnits.add(rowUnit);
            }
        }

        for (Unit unit : affectedUnits) {
            int totalStr = unit.getBaseStrength() * (int) Math.pow(2, affectedUnits.size() - 1);
            unit.setEffectStrength(totalStr - unit.getBaseStrength());
        }
    }
    
    @Override
    public Unit getUnit() {
        if (card instanceof UnitCard) {
            return ((UnitCard) card).getUnit();
        }
        return null;
    }

    @Override
    public String getName() {
        return "Tight bond";
    }

    @Override
    public String getDescription() {
        return "Double the strength of units in the same row with the same name";
    }

}
