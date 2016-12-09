package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.game.Gwent;

/**
 * In the beginning the player can redraw up to 2 cards.
 * @author Daniel
 */
public class RedrawState extends GameStateAdapter {

    public RedrawState(Gwent game) {
        super(game);
    }
}
