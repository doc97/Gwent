package fi.riissanen.gwent.game;

import static fi.riissanen.gwent.game.MatchManager.Result.DRAW;
import static fi.riissanen.gwent.game.MatchManager.Result.LOSS;
import static fi.riissanen.gwent.game.MatchManager.Result.NONE;
import static fi.riissanen.gwent.game.MatchManager.Result.WIN;

/**
 * Manages round results and match result.
 * @author Daniel
 */
public class MatchManager {

    public enum Result {
        WIN, LOSS, DRAW, NONE;
    }
    
    public static final int MAX_LIVES = 2;
    
    private int friendlyLives = MAX_LIVES;
    private int enemyLives = MAX_LIVES;
    private Result roundResult = NONE;
    
    public void reset() {
        friendlyLives = MAX_LIVES;
        enemyLives = MAX_LIVES;
    }
    
    public void setFriendlyRoundStatus(Result roundResult) {
        if (roundResult != null) {
            this.roundResult = roundResult;
        }
    }
    
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
