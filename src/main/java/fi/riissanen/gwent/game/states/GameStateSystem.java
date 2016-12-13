package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.game.Gwent;
import fi.riissanen.gwent.game.events.CardPlayedEvent;
import fi.riissanen.gwent.game.events.CardStageEvent;
import fi.riissanen.gwent.game.events.DrawCardEvent;
import fi.riissanen.gwent.game.events.EventSystem;
import fi.riissanen.gwent.game.events.StateChangeEvent;
import static fi.riissanen.gwent.game.states.GameStates.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Handles game state transitions.
 * @author Daniel
 */
public class GameStateSystem {

    private final Map<GameStates, GameState> states = new HashMap<>();
    private final GameState emptyState;
    private final Stack<GameState> stack = new Stack();
    private final Gwent game;
    private GameStates pushState;
    private boolean popState;
    
    /**
     * Creates an empty game state to use to avoid NullpointerExceptions.
     * @param game The game instance
     */
    public GameStateSystem(Gwent game) {
        this.game = game;
        emptyState = new GameState() {
            @Override
            public void createGUI() {}
            @Override
            public void create() {}
            @Override
            public void enter() {}
            @Override
            public void exit() {}
            @Override
            public void destroy() {}
        };
    }
    
    /**
     * Initialises states and adds them to a map.
     */
    public void initialize() {
        EventSystem eventSys = game.getEventSystem();
        NormalState normalState = new NormalState(game);
        StageState stageState = new StageState(game);
        eventSys.addListener(DrawCardEvent.class, normalState);
        eventSys.addListener(CardStageEvent.class, normalState);
        eventSys.addListener(CardPlayedEvent.class, normalState);
        eventSys.addListener(CardStageEvent.class, stageState);
        eventSys.addListener(CardPlayedEvent.class, stageState);
        
        states.put(CHOOSE_STARTING_PLAYER_STATE, new ChooseStartingPlayerState(game));
        states.put(REDRAW_STATE, new RedrawState(game));
        states.put(NORMAL_STATE, normalState);
        states.put(STAGE_STATE, stageState);
        states.put(DISCARD_PILE_STATE, new DiscardPileState(game));
        
        if (Engine.INSTANCE.isInitialized()) {
            for (GameStates state : GameStates.values()) {
                states.get(state).createGUI();
            }
        }
    }
    
    /**
     * Pushes a state onto the stack.
     * 
     * <p>
     * Will fail if the state has not been loaded or if it already is in the stack
     * @param stateKey The key to state
     */
    public void push(GameStates stateKey) {
        GameState nextState = states.get(stateKey);
        if (stack.contains(nextState) || nextState == null) {
            return;
        }
        
        GameState oldState = getCurrentState();
        oldState.exit();
        stack.push(nextState);
        nextState.create();
        nextState.enter();
        
        reportStateChangeEvent(oldState, nextState);
    }
    
    /**
     * Pops a state from the stack unless it is empty.
     */
    public void pop() {
        if (stack.size() >= 1) {
            GameState oldState = stack.pop();
            GameState newState = getCurrentState();
            oldState.exit();
            oldState.destroy();
            newState.enter();
            reportStateChangeEvent(oldState, newState);
        }
    }
    
    /**
     * Queue a pop() next update.
     */
    public void popNext() {
        popState = true;
    }
    
    /**
     * Queue a push(GameStates) next update.
     * @param state The state to push
     */
    public void pushNext(GameStates state) {
        pushState = state;
    }
    
    /**
     * Pop operation will be executed first.
     */
    public void update() {
        if (popState) {
            pop();
            popState = false;
        }
        if (pushState != null) {
            push(pushState);
            pushState = null;
        }
    }
    
    public Set<GameStates> getLoadedStates() {
        return states.keySet();
    }
    
    public GameState getCurrentState() {
        return stack.isEmpty() ? emptyState : stack.peek();
    }
    
    /**
     * Checks if the current state is connected to a key.
     * @param stateKey The key with which to check
     * @return True if the current state is connected to the key
     */
    public boolean isCurrentState(GameStates stateKey) {
        if (!states.containsKey(stateKey)) {
            return false;
        }
        return states.get(stateKey).equals(getCurrentState());
    }
    
    private void reportStateChangeEvent(GameState oldState, GameState newState) {
        game.getEventSystem().register(new StateChangeEvent(oldState, newState));
    }
}
