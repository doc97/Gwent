package fi.riissanen.gwent.game.factions;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Player;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.events.RoundEndEvent;

/**
 *
 * @author Daniel
 */
public class NorthernKingdoms extends Faction {

    private final Ability ability;
    private boolean triggered;
    
    public NorthernKingdoms(Player player) {
        ability = new Ability() {
            @Override
            public void activate(GameSystem system) {
                player.drawCard();
            }

            @Override
            public String getName() {
                return "Northern Kingdoms ability";
            }

            @Override
            public String getDescription() {
                return "Grants an extra card upon winning a round";
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
