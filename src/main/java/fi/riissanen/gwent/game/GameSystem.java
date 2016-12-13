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
import fi.riissanen.gwent.game.events.CardStageEvent;
import fi.riissanen.gwent.game.states.GameStateSystem;
import static fi.riissanen.gwent.game.states.GameStates.CHOOSE_STARTING_PLAYER_STATE;
import static fi.riissanen.gwent.game.states.GameStates.STAGE_STATE;
import static fi.riissanen.gwent.game.states.GameStates.NORMAL_STATE;

/**
 * In charge of handling the flow of the game.
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
    
    /**
     * Creates a game system with a reference to the game.
     * @param game 
     */
    public GameSystem(Gwent game) {
        this.game = game;
    }
    
    /**
     * Initializes the system and all sub-systems.
     * @param friendly The friendly player
     * @param enemy The enemy player
     */
    public void initialize(Player friendly, Player enemy) {
        this.friendly = friendly;
        this.enemy = enemy;
        this.friendly.drawCards(10);
        this.enemy.drawCards(10);
        board = new GameBoard();
        stateSystem = new GameStateSystem(game);
        matchManager = new MatchManager();
        
        stateSystem.initialize();
        stateSystem.push(NORMAL_STATE);
        stateSystem.push(CHOOSE_STARTING_PLAYER_STATE);
    }
    
    /**
     * Sets the player in turn.
     * 
     * <p>
     * Can only be used when in CHOOSE_STARTING_PLAYER_STATE GameState
     * @param friendlyInTurn If the friendly player should be in turn
     */
    public void setPlayerInTurn(boolean friendlyInTurn) {
        if (stateSystem.isCurrentState(CHOOSE_STARTING_PLAYER_STATE)) {
            friendly.setInTurn(friendlyInTurn);
            enemy.setInTurn(!friendlyInTurn);
            stateSystem.popNext();
        }
    }
    
    /**
     * Swaps the player in turn.
     */
    public void switchTurn() {
        friendly.setInTurn(!friendly.isInTurn());
        enemy.setInTurn(!enemy.isInTurn());
    }
    
    /**
     * Stages a card.
     * 
     * <p>
     * A card must be staged before it can be played.
     * @param card The card to stage
     */
    public void stageCard(Card card) {
        if (card != null) {
            cancelStagedCard();
            stagedCard = card;
            stateSystem.push(STAGE_STATE);
            game.getEventSystem().register(new CardStageEvent(stagedCard, true));
        }
    }
    
    /**
     * Unstages the currently staged card if any.
     */
    public void unstageCard() {
        if (stateSystem.isCurrentState(STAGE_STATE)) {
            stateSystem.pop();
        }
        stagedCard = null;
    }
    
    /**
     * Unstages the currently staged card.
     */
    public void cancelStagedCard() {
        if (stagedCard != null) {
            game.getEventSystem().register(new CardStageEvent(stagedCard, false));
        }
        unstageCard();
    }
    
    /**
     * Plays the staged card.
     * @param rowIndex On which row the card will be played
     * @return The success of the play
     */
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
        
        game.getEventSystem().register(new CardPlayedEvent(stagedCard, rowIndex));
        unstageCard();
        return true;
    }
    
    /**
     * Prints the status in a text format.
     */
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
    
    public Card getStagedCard() {
        return stagedCard;
    }
}
