package fi.riissanen.gwent.game.cards.abilities;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.CombatRow;
import fi.riissanen.gwent.game.combat.Unit;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class MoraleBoost implements Ability, UnitAbility {

    private final Card card;
    
    public MoraleBoost(Card card) {
        this.card = card;
    }
    
    @Override
    public void activate(GameSystem system) {
        CombatRow row = system.getBoard().getRow(getUnit());
        List<Unit> units = row.getUnits();
        List<Unit> moraleUnits = row.getUnitsWithAbility(MoraleBoost.class);
        for (Unit rowUnit : units) {
            rowUnit.setEffectStrength((moraleUnits.size() - 1));
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
        return "Morale boost";
    }

    @Override
    public String getDescription() {
        return "Increase strength of all other units in a row by one";
    }

}
