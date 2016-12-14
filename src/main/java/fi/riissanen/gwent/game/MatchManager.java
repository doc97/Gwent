package fi.riissanen.gwent.game;

import static fi.riissanen.gwent.game.MatchManager.Result.DRAW;
import static fi.riissanen.gwent.game.MatchManager.Result.LOSS;
import static fi.riissanen.gwent.game.MatchManager.Result.NONE;
import static fi.riissanen.gwent.game.MatchManager.Result.WIN;
import fi.riissanen.gwent.game.events.AfterEvent;
import fi.riissanen.gwent.game.events.Event;
import fi.riissanen.gwent.game.events.EventListener;
import fi.riissanen.gwent.game.events.RoundEndEvent;

/**
 * Manages round results and match result.
 * @author Daniel
 */
public class MatchManager implements EventListener {

    @Override
    public void process(Event event) {
        if (event instanceof AfterEvent) {
            Class<? extends Event> firedEvent = ((AfterEvent) event).getEvent();
            if (firedEvent.equals(RoundEndEvent.class)) {
                finishRound();
            }
        }
    }

    public enum Result {
        WIN, LOSS, DRAW, NONE;
    }
    
    public static final int MAX_LIVES = 2;
    
    private int friendlyLives = MAX_LIVES;
    private int enemyLives = MAX_LIVES;
    private Result roundResult = NONE;
    
    /**
     * Resets lives.
     */
    public void reset() {
        friendlyLives = MAX_LIVES;
        enemyLives = MAX_LIVES;
    }
    
    /**
     * Sets the current round result.
     * @param roundResult The result for the friendly player
     */
    public void setFriendlyRoundStatus(Result roundResult) {
        if (roundResult != null) {
            this.roundResult = roundResult;
        }
    }
    
    /**
     * Depending on the round result lives may be reduced.
     * 
     * <p>
     * The round result is reset back to NONE.
     */
    public void finishRound() {
        switch (roundResult) {
            case WIN:
                enemyLives--;
                break;
            case LOSS:
                friendlyLives--;
                break;
            case DRAW:
                friendlyLives--;
                enemyLives--;
                break;
            default:
                break;
        }
        roundResult = NONE;
    }
    
    public int getFriendlyLives() {
        return friendlyLives;
    }
    
    public int getEnemyLives() {
        return enemyLives;
    }
    
    /**
     * Returns the match result for the friendly player.
     * @return The result
     * @see Result
     */
    public Result getMatchResult() {
        if (friendlyLives > 0 && enemyLives > 0) {
            return NONE;
        } else if (friendlyLives == 0 && enemyLives == 0) {
            return DRAW;
        } else if (friendlyLives <= 0) {
            return LOSS;
        } else {
            return WIN;
        }
    }
}
