package fi.riissanen.gwent.game.factions;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.combat.CombatRow;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.events.RoundEndEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A faction in the game.
 * @author Daniel
 */
public class Monsters extends Faction {

    private final Ability ability;
    private boolean triggered;
    
    /**
     * Creates the Monster faction ability.
     * @param player The player to which the faction belongs to 
     */
    public Monsters(Player player) {
        ability = new Ability() {
            @Override
            public void activate(GameSystem system) {
                Random rand = new Random();
                List<Integer> indices = new ArrayList<>();
                for (int i = 0; i < UnitType.values().length; i++) {
                    indices.add(i);
                }
                int rowIndex;
                CombatRow row;
                do {
                    rowIndex = rand.nextInt(indices.size());
                    row = system.getBoard().getRows(player.isFriendly())[rowIndex];
                    if (row.getUnitCount() > 0) {
                        break;
                    } else {
                        indices.remove(rowIndex);
                    }
                } while (indices.size() > 0);
                
                if (indices.size() > 0) {
                    int unitIndex = rand.nextInt(row.getUnitCount());
                    Unit unit = row.getUnits().get(unitIndex);
                    system.getBoard().saveUnit(unit);
                }
            }

            @Override
            public String getName() {
                return "Monsters ability";
            }

            @Override
            public String getDescription() {
                return "Keeps one random unit card on the battlefield after each round";
            }
        };
    }
    
    @Override
    public Ability getAbility() {
        return ability;
    }

    @Override
    public boolean isTriggered() {
        return triggered;
    }

    @Override
    public void process(Event event) {
        if (event instanceof RoundEndEvent) {
            triggered = true;
        }
    }

}
