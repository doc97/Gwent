package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.Player;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestRoundEndEvent {

    private RoundEndEvent event;
    
    @Test
    public void testGetRoundWinner() {
        Player player = new Player(true);
        event = new RoundEndEvent(player);
        assertEquals(player, event.getRoundWinner());
    }
}
