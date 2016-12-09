package fi.riissanen.gwent.game.states;

/**
 * Provides methods for game states.
 * @author Daniel
 */
public interface GameState {
    /**
     * Called when the state is added to the state stack.
     */
    public void create();
    
    /**
     * Called when the state is entered.
     */
    public void enter();
    
    /**
     * Called when the state is exited.
     */
    public void exit();
    
    /**
     * Called when the state is removed from the state stack.
     */
    public void destroy();
}
