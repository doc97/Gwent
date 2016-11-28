package fi.riissanen.gwent.game;

import static fi.riissanen.gwent.game.MatchManager.Result.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestMatchManager {

    private final MatchManager manager = new MatchManager();
    
    @Test
    public void testReset() {
        manager.setFriendlyRoundStatus(WIN);
        manager.finishRound();
        manager.setFriendlyRoundStatus(LOSS);
        manager.finishRound();
        manager.reset();
        assertEquals(MatchManager.MAX_LIVES, manager.getFriendlyLives());
        assertEquals(MatchManager.MAX_LIVES, manager.getEnemyLives());
    }
    
    @Test
    public void testFinishRoundWin() {
        manager.setFriendlyRoundStatus(WIN);
        manager.finishRound();
        assertEquals(MatchManager.MAX_LIVES, manager.getFriendlyLives());
        assertEquals(MatchManager.MAX_LIVES - 1, manager.getEnemyLives());
    }
    
    @Test
    public void testFinishRoundLoss() {
        manager.setFriendlyRoundStatus(LOSS);
        manager.finishRound();
        assertEquals(MatchManager.MAX_LIVES - 1, manager.getFriendlyLives());
        assertEquals(MatchManager.MAX_LIVES, manager.getEnemyLives());
    }
    
    @Test
    public void testFinishRoundDraw() {
        manager.setFriendlyRoundStatus(DRAW);
        manager.finishRound();
        assertEquals(MatchManager.MAX_LIVES - 1, manager.getFriendlyLives());
        assertEquals(MatchManager.MAX_LIVES - 1, manager.getEnemyLives());
    }
    
    @Test
    public void testFinishRoundNone() {
        manager.setFriendlyRoundStatus(NONE);
        manager.finishRound();
        assertEquals(MatchManager.MAX_LIVES, manager.getFriendlyLives());
        assertEquals(MatchManager.MAX_LIVES, manager.getEnemyLives());
    }
    
    @Test
    public void testFinishRoundNull() {
        manager.setFriendlyRoundStatus(null);
        manager.finishRound();
        assertEquals(MatchManager.MAX_LIVES, manager.getFriendlyLives());
        assertEquals(MatchManager.MAX_LIVES, manager.getEnemyLives());
    }
    
    @Test
    public void testGetMatchResultWin() {
        for (int i = 0; i < MatchManager.MAX_LIVES; i++) {
            manager.setFriendlyRoundStatus(WIN);
            manager.finishRound();
        }
        assertEquals(WIN, manager.getMatchResult());
    }
    
    @Test
    public void testGetMatchResultLoss() {
        for (int i = 0; i < MatchManager.MAX_LIVES; i++) {
            manager.setFriendlyRoundStatus(LOSS);
            manager.finishRound();
        }
        assertEquals(LOSS, manager.getMatchResult());
    }
    
    @Test
    public void testGetMatchResultDraw() {
        for (int i = 0; i < MatchManager.MAX_LIVES; i++) {
            manager.setFriendlyRoundStatus(DRAW);
            manager.finishRound();
        }
        assertEquals(DRAW, manager.getMatchResult());

        manager.reset();
        for (int i = 0; i < MatchManager.MAX_LIVES - 1; i++) {
            manager.setFriendlyRoundStatus(WIN);
            manager.finishRound();
            manager.setFriendlyRoundStatus(LOSS);
            manager.finishRound();
        }
        manager.setFriendlyRoundStatus(DRAW);
        manager.finishRound();
        assertEquals(DRAW, manager.getMatchResult());
    }
    
    @Test
    public void testGetMatchResultNone() {
        for (int i = 0; i < MatchManager.MAX_LIVES - 1; i++) {
            manager.setFriendlyRoundStatus(DRAW);
            manager.finishRound();
            assertEquals(NONE, manager.getMatchResult());
        }
    }
}
