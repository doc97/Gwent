package fi.riissanen.gwent.game.factions;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.states.GameStates;
import fi.riissanen.gwent.game.events.MatchStartEvent;

/**
 * A faction in the game
 * @author Daniel
 */
public class Scoiatael extends Faction {

    private final Ability ability;
    private boolean triggered;
    
    public Scoiatael() {
        ability = new Ability() {
            @Override
            public void activate(GameSystem system) {
                system.getStateSystem().push(GameStates.CHOOSE_STARTING_PLAYER_STATE);
            }

            @Override
            public String getName() {
                return "Scoia'tael ability";
            }

            @Override
            public String getDescription() {
                return "Decides who startes the round";
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
        if (event instanceof MatchStartEvent) {
            triggered = true;
        }
    }
}
