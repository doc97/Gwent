package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.game.GameSystem;

/**
 * State when the starting player is chosen
 * 
 * <p>
 * If a player is playing with {@link Scoiatael} then they get to choose who
 * starts, otherwise it is random
 * @author Daniel
 */
public class ChooseStartingPlayerState implements GameState {

    private final GameSystem gameSys;
    
    public ChooseStartingPlayerState(GameSystem gameSys) {
        this.gameSys = gameSys;
    }
    
    @Override
    public void enter() {
        gameSys.setPlayerInTurn(true);
    }

    @Override
    public void exit() {
    }
}
