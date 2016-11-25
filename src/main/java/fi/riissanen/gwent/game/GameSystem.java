package fi.riissanen.gwent.game;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.Logger;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.combat.GameBoard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import fi.riissanen.gwent.game.events.CardPlayedEvent;
import fi.riissanen.gwent.game.states.GameStateSystem;
import static fi.riissanen.gwent.game.states.GameStates.CHOOSE_STARTING_PLAYER_STATE;
import static fi.riissanen.gwent.game.states.GameStates.HAND_STATE;
import static fi.riissanen.gwent.game.states.GameStates.STAGE_STATE;

/**
 * In charge of handling the flow of the game
 *
 * @author Daniel
 */
public class GameSystem {

    private GameStateSystem stateSystem;
    private MatchManager matchManager;
    private GameBoard board;
    private Player friendly;
    private Player enemy;
    private Card stagedCard;
    private final Gwent game;
    
    public GameSystem(Gwent game) {
        this.game = game;
    }
    
    public void initialize(Player friendly, Player enemy) {
        this.friendly = friendly;
        this.enemy = enemy;
        this.friendly.drawCards(10);
        this.enemy.drawCards(10);
        board = new GameBoard();
        stateSystem = new GameStateSystem(game);
        matchManager = new MatchManager();
        
        stateSystem.initialize();
        stateSystem.push(HAND_STATE);
        stateSystem.push(CHOOSE_STARTING_PLAYER_STATE);
    }
    
    public void setPlayerInTurn(boolean friendlyInTurn) {
        if (stateSystem.isCurrentState(CHOOSE_STARTING_PLAYER_STATE)) {
            friendly.setInTurn(friendlyInTurn);
            enemy.setInTurn(!friendlyInTurn);
            stateSystem.pop();
        }
    }
    
    public void switchTurn() {
        friendly.setInTurn(!friendly.isInTurn());
        enemy.setInTurn(!enemy.isInTurn());
    }
    
    public void stageCard(Card card) {
        if (card != null) {
            stagedCard = card;
            stateSystem.push(STAGE_STATE);
        }
    }
    
    public void unstageCard() {
        stagedCard = null;
        if (stateSystem.isCurrentState(STAGE_STATE)) {
            stateSystem.pop();
        }
    }
    
    public boolean playCard(int rowIndex) {
        if (!stateSystem.isCurrentState(STAGE_STATE)) {
            return false;
        }
        
        if (stagedCard instanceof UnitCard) {
            if (rowIndex == -1) {
                throw new IllegalStateException("You must first pick a row");
            }
            
            UnitCard card = (UnitCard) stagedCard;
            Unit unit = card.getUnit();
            UnitType row = UnitType.values()[rowIndex];
            board.addUnit(unit, row, unit.isFriendly());
            friendly.removeCardFromHand(stagedCard);
        }
        
        for (Ability ability : stagedCard.getAbilities()) {
            ability.activate(this);
        }
        
        if (game != null && game.getEventSystem() != null) {
            game.getEventSystem().register(new CardPlayedEvent(stagedCard));
        }
        unstageCard();
        return true;
    }
    
    public void status() {
        String staged = "None";
        if (stateSystem.isCurrentState(STAGE_STATE)) {
            staged = stagedCard.toString();
        }
        Engine.INSTANCE.log.write(Logger.LogLevel.INFO, "Staged card: " + staged);
        board.status();
    }
    
    public GameStateSystem getStateSystem() {
        return stateSystem;
    }
    
    public MatchManager getMatchManager() {
        return matchManager;
    }
    
    public GameBoard getBoard() {
        return board;
    }
    
    public Player getFriendlyPlayer() {
        return friendly;
    }
    
    public Player getEnemyPlayer() {
        return enemy;
    }
}
