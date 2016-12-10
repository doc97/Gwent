package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.MatchManager.Result;

/**
 * Fired when a round is over.
 * @author Daniel
 */
public class RoundEndEvent implements Event {
    
    private final Result result;
    
    /**
     * Constructor.
     * @param result The result of the round.
     */
    public RoundEndEvent(Result result) {
        this.result = result;
    }
    
    public Result getRoundResult() {
        return result;
    }
}
