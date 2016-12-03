package fi.riissanen.gwent.game.factions;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.MatchManager.Result;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.events.RoundEndEvent;

/**
 * A faction in the game
 * @author Daniel
 */
public class NilfgaardianEmpire extends Faction {

    private final Ability ability;
    private boolean triggered;
    
    public NilfgaardianEmpire(Player player) {
        ability = new Ability() {
            @Override
            public void activate(GameSystem system) {
                Result result = player.isFriendly() ? Result.WIN : Result.LOSS;
                system.getMatchManager().setFriendlyRoundStatus(result);
            }

            @Override
            public String getName() {
                return "Nilfgaardian Empire ability";
            }

            @Override
            public String getDescription() {
                return "Wins any round ending in draw";
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
            if (((RoundEndEvent) event).getRoundResult() == Result.DRAW) {
                triggered = true;
            }
        }
    }
}
