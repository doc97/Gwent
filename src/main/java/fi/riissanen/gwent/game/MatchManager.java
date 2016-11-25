package fi.riissanen.gwent.game;

import static fi.riissanen.gwent.game.MatchManager.Result.DRAW;
import static fi.riissanen.gwent.game.MatchManager.Result.LOSS;
import static fi.riissanen.gwent.game.MatchManager.Result.WIN;

/**
 *
 * @author Daniel
 */
public class MatchManager {

    public enum Result {
        WIN, LOSS, DRAW;
    }
    
    private int friendlyLives;
    private int enemyLives;
    private Result result;
    
    public void setFriendlyRoundStatus(Result result) {
        if (result != null) {
            this.result = result;
        }
    }
    
    public void finishRound() {
        switch (result) {
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
    }
    
    public int getFriendlyLives() {
        return friendlyLives;
    }
    
    public int getEnemyLives() {
        return enemyLives;
    }
    
    public boolean matchIsDraw() {
        return friendlyLives == 0 && enemyLives == 0;
    }
    
    public boolean matchIsWon() {
        return friendlyLives > 0 && enemyLives == 0;
    }
}
