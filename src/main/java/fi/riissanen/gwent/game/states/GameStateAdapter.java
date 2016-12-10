package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.game.Gwent;

/**
 * Adapter for {@link GameState}
 * @author Daniel
 */
public class GameStateAdapter implements GameState {

    protected final Gwent game;
    
    public GameStateAdapter(Gwent game) {
        this.game = game;
    }
    
    @Override
    public void createGUI() {    
    }
    
    @Override
    public void create() {
    }
    
    @Override
    public void enter() {
    }

    @Override
    public void exit() {
    }
    
    public void destroy() {
    }
}
