package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.game.GameSystem;

/**
 *
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
