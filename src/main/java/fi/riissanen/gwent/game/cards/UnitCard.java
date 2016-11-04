package fi.riissanen.gwent.game.cards;

import fi.riissanen.gwent.game.combat.Unit;

/**
 * A {@link Card} that contains a unit
 *
 * @author Daniel
 */
public class UnitCard implements Card {

    private final Unit unit;

    private UnitCard(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }
}
