package fi.riissanen.gwent.game.factions;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.combat.CombatRow;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.events.RoundEndEvent;
import java.util.Random;

/**
 *
 * @author Daniel
 */
public class Monsters extends Faction {

    private final Ability ability;
    private boolean triggered;
    
    public Monsters(Player player) {
        ability = new Ability() {
            @Override
            public void activate(GameSystem system) {
                Random rand = new Random();
                int rowIndex = rand.nextInt(3);
                CombatRow row = system.getBoard().getRows(player.isFriendly())[rowIndex];
                int unitIndex = rand.nextInt(row.getUnitCount());
                Unit unit = row.getUnits().get(unitIndex);
                system.getBoard().saveUnit(unit);
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
