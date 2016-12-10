package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.events.MatchStartEvent;
import fi.riissanen.gwent.game.factions.Scoiatael;

/**
 * State when the starting player is chosen.
 * 
 * <p>
 * If a player is playing with {@link Scoiatael} then they get to choose who
 * starts, otherwise it is random
 * @author Daniel
 */
public class ChooseStartingPlayerState extends GameStateAdapter {

    private final GameSystem gameSys;
    
    /**
     * Constructor.
     * @param game The game instance
     */
    public ChooseStartingPlayerState(Gwent game) {
        super(game);
        gameSys = game.getGameSystem();
    }
    
    @Override
    public void enter() {
        gameSys.setPlayerInTurn(true);
    }

    @Override
    public void exit() {
        game.getEventSystem().register(new MatchStartEvent());
    }
}
