package fi.riissanen.gwent.game.states;

import fi.riissanen.gwent.game.Gwent;

/**
 * Entered when the match is over.
 * @author Daniel
 */
public class MatchOverState extends GameStateAdapter {

    /**
     * Constructor.
     * @param game The game instance
     */
    public MatchOverState(Gwent game) {
        super(game);
    }

    @Override
    public void enter() {
        System.out.println("Result: " + game.getGameSystem().getMatchManager().getMatchResult());
    }
}
