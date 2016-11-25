package fi.riissanen.gwent.game.cards.neutral;

import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.combat.CombatRow;
import fi.riissanen.gwent.game.combat.GameBoard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import java.util.ArrayList;
import java.util.List;

/**
 * A special card that also is neutral, meaning it is accessible to all factions
 * @author Daniel
 */
public class WeatherCard implements Card {

    private Player owner;
    private final List<Ability> abilities = new ArrayList<>();
    private final UnitType row;
    private final String name;
    
    public WeatherCard(String name, UnitType row) {
        this.name = name;
        this.row = row;
    }
    
    public void setOwner(Player owner) {
        this.owner = owner;
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
    
    @Override
    public List<Ability> getAbilities() {
        return abilities;
    }
    
    @Override
    public Player getOwner() {
        return owner;
    }
    
    @Override
    public String getName() {
        return name;
    }
}
