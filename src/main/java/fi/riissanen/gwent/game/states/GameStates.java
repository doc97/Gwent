package fi.riissanen.gwent.game.states;

/**
 * Enumerations of possible game states.
 * 
 * <p>
 * They also work as keys for the FSM in {@link GameStateSystem}
 * @author Daniel
 */
public enum GameStates {
    CHOOSE_STARTING_PLAYER_STATE,
    REDRAW_STATE,
    NORMAL_STATE,
    STAGE_STATE,
    DISCARD_PILE_STATE;
}
