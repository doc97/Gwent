package fi.riissanen.gwent.game.states;

import static fi.riissanen.gwent.game.states.GameStates.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Daniel
 */
public class GameStateSystem {

    public Map<GameStates, GameState> states = new HashMap<>();
    private final Stack<GameState> stack = new Stack();
    
    public void initialize() {
        states.put(HAND_STATE, new HandState());
        states.put(STAGE_STATE, new StageState());
        states.put(DISCARD_PILE_STATE, new DiscardPileState());
    }
    
    public void next(GameStates stateKey) {
        GameState nextState = states.get(stateKey);
        if (stack.contains(nextState)) {
            return;
        }
        
        if (!stack.isEmpty()) {
            stack.peek().exit();
        }
        
        if (nextState != null) {
            stack.push(nextState).enter();
        }
    }
    
    public void previous() {
        stack.pop().exit();
        stack.peek().enter();
    }
    
    public GameState getCurrentState() {
        return stack.peek();
    }
    
    public boolean isCurrentState(GameStates stateKey) {
        return states.get(stateKey).equals(getCurrentState());
        
    }
}
