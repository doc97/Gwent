package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.game.Gwent;

/**
 * When the player wants to play a card he stages it.
 * @author Daniel
 */
public class StageState extends GameStateAdapter {

    /**
     * Constructor.
     * @param game The game instance
     */
    public StageState(Gwent game) {
        super(game);
    }
}
