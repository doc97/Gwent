package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.Player;

/**
 *
 * @author Daniel
 */
public class RoundEndEvent implements Event {
    
    private Player winner;
    
    public RoundEndEvent(Player winner) {
        this.winner = winner;
    }
    
    public Player getRoundWinner() {
        return winner;
    }
}
