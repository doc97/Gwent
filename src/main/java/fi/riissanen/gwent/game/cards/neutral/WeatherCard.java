package fi.riissanen.gwent.game.cards.neutral;

import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.combat.CombatRow;
import fi.riissanen.gwent.game.combat.GameBoard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;

/**
 *
 * @author Daniel
 */
public class WeatherCard implements Card {

    private final UnitType row;
    
    public WeatherCard(UnitType row) {
        this.row = row;
    }
    
    public void activate(GameBoard board) {
        CombatRow friendly = board.getRow(true, row);
        CombatRow enemy = board.getRow(false, row);
        for (Unit unit : friendly.getUnits()) {
            unit.setBaseStrength(1);
        }
        for (Unit unit : enemy.getUnits()) {
            unit.setBaseStrength(1);
        }
    }
}
