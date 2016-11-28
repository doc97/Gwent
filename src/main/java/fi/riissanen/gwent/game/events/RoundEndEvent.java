package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.Player;

/**
 * Fired when a round is over
 * @author Daniel
 */
public class RoundEndEvent implements Event {
    
    private final Player winner;
    
    public RoundEndEvent(Player winner) {
        this.winner = winner;
    }
    
    public Player getRoundWinner() {
        return winner;
    }
}
