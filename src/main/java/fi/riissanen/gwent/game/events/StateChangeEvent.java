package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.states.GameState;

/**
 * Fired when a {@link GameState} changes.
 * @author Daniel
 */
public class StateChangeEvent implements Event {
    
    private final GameState newState;
    private final GameState oldState;
    
    /**
     * Constructor.
     * @param oldState The old state from which we moved
     * @param newState The new state to which we moved
     */
    public StateChangeEvent(GameState oldState, GameState newState) {
        this.newState = newState;
        this.oldState = oldState;
    }
    
    public GameState getNewState() {
        return newState;
    }
    
    public GameState getOldState() {
        return oldState;
    }
}
