package fi.riissanen.gwent.game.events;

import fi.riissanen.gwent.game.MatchManager.Result;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestRoundEndEvent {

    private RoundEndEvent event;
    
    @Test
    public void testGetRoundResultWin() {
        event = new RoundEndEvent(Result.WIN);
        assertEquals(Result.WIN, event.getRoundResult());
    }
    
    @Test
    public void testGetRoundResultLoss() {
        event = new RoundEndEvent(Result.LOSS);
        assertEquals(Result.LOSS, event.getRoundResult());
    }
    
    @Test
    public void testGetRoundResultDraw() {
        event = new RoundEndEvent(Result.DRAW);
        assertEquals(Result.DRAW, event.getRoundResult());
    }
    
    @Test
    public void testGetRoundResultNone() {
        event = new RoundEndEvent(Result.NONE);
        assertEquals(Result.NONE, event.getRoundResult());
    }
}
