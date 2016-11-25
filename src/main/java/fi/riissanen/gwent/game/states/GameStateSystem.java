package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.events.StateChangeEvent;
import static fi.riissanen.gwent.game.states.GameStates.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Handles game state transitions
 * @author Daniel
 */
public class GameStateSystem {

    private final Map<GameStates, GameState> states = new HashMap<>();
    private final Stack<GameState> stack = new Stack();
    private final Gwent game;
    
    public GameStateSystem(Gwent game) {
        this.game = game;
    }
    
    public void initialize() {
        if (game != null && game.getGameSystem() != null) {
            states.put(CHOOSE_STARTING_PLAYER_STATE, new ChooseStartingPlayerState(game.getGameSystem()));
        }
        states.put(REDRAW_STATE, new RedrawState());
        states.put(HAND_STATE, new HandState());
        states.put(STAGE_STATE, new StageState());
        states.put(DISCARD_PILE_STATE, new DiscardPileState());
    }
    
    public void push(GameStates stateKey) {
        GameState oldState = null;
        GameState nextState = states.get(stateKey);
        if (stack.contains(nextState)) {
            return;
        }
        
        if (!stack.isEmpty()) {
            oldState = stack.peek();
            stack.peek().exit();
        }
        
        if (nextState != null) {
            stack.push(nextState).enter();
        }
        if (game != null && game.getEventSystem() != null) {
            game.getEventSystem().register(new StateChangeEvent(oldState, nextState));
        }
    }
    
    public void pop() {
        if (stack.size() > 1) {
            GameState oldState = stack.pop();
            GameState newState = stack.peek();
            oldState.exit();
            newState.enter();
            if (game != null && game.getEventSystem() != null) {
                game.getEventSystem().register(new StateChangeEvent(oldState, newState));
            }
        }
    }
    
    public GameState getCurrentState() {
        return stack.peek();
    }
    
    public boolean isCurrentState(GameStates stateKey) {
        return states.get(stateKey).equals(getCurrentState());
        
    }
}
