package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.events.EventSystem;
import fi.riissanen.gwent.game.events.StateChangeEvent;
import static fi.riissanen.gwent.game.states.GameStates.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Handles game state transitions
 * @author Daniel
 */
public class GameStateSystem {

    private final Map<GameStates, GameState> states = new HashMap<>();
    private final GameState emptyState;
    private final Stack<GameState> stack = new Stack();
    private EventSystem eventSystem;
    
    public GameStateSystem() {
        emptyState = new GameState() {
            @Override
            public void enter() {}
            @Override
            public void exit() {}
        };
    }
    
    public void initialize(GameSystem system) {
        if (system != null) {
            states.put(CHOOSE_STARTING_PLAYER_STATE, new ChooseStartingPlayerState(system));
        }
        states.put(REDRAW_STATE, new RedrawState());
        states.put(HAND_STATE, new HandState());
        states.put(STAGE_STATE, new StageState());
        states.put(DISCARD_PILE_STATE, new DiscardPileState());
    }
    
    public void setEventSystem(EventSystem eventSystem) {
        this.eventSystem = eventSystem;
    }
    
    public void push(GameStates stateKey) {
        GameState nextState = states.get(stateKey);
        if (stack.contains(nextState) || nextState == null) {
            return;
        }
        
        GameState oldState = getCurrentState();
        oldState.exit();
        stack.push(nextState).enter();
        
        reportStateChangeEvent(oldState, nextState);
    }
    
    public void pop() {
        if (stack.size() >= 1) {
            GameState oldState = stack.pop();
            GameState newState = getCurrentState();
            oldState.exit();
            newState.enter();
            reportStateChangeEvent(oldState, newState);
        }
    }
    
    public Set<GameStates> getLoadedStates() {
        return states.keySet();
    }
    
    public GameState getCurrentState() {
        return stack.isEmpty() ? emptyState : stack.peek();
    }
    
    public boolean isCurrentState(GameStates stateKey) {
        if (!states.containsKey(stateKey)) {
            return false;
        }
        return states.get(stateKey).equals(getCurrentState());
    }
    
    private void reportStateChangeEvent(GameState oldState, GameState newState) {
        if (eventSystem != null) {
            eventSystem.register(new StateChangeEvent(oldState, newState));
        }
    }
}
