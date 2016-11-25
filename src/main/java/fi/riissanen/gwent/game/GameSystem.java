package fi.riissanen.gwent.game;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.Logger;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.cards.abilities.Ability;
import fi.riissanen.gwent.game.combat.GameBoard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import fi.riissanen.gwent.game.states.GameStateSystem;
import static fi.riissanen.gwent.game.states.GameStates.HAND_STATE;
import static fi.riissanen.gwent.game.states.GameStates.REDRAW_STATE;
import static fi.riissanen.gwent.game.states.GameStates.STAGE_STATE;

/**
 * In charge of handling the flow of the game
 *
 * @author Daniel
 */
public class GameSystem {

    private GameStateSystem stateSystem;
    private GameBoard board;
    private Player player;
    private Card stagedCard;

    public void initialize(Player player) {
        this.player = player;
        this.player.drawCards(10);
        this.player.setInTurn(true);
        board = new GameBoard();
        stateSystem = new GameStateSystem();
        stateSystem.next(HAND_STATE);
        stateSystem.next(REDRAW_STATE);
    }
    
    public void stageCard(Card card) {
        if (card != null) {
            stagedCard = card;
            stateSystem.next(STAGE_STATE);
        }
    }
    
    public void unstageCard() {
        stagedCard = null;
        if (stateSystem.isCurrentState(STAGE_STATE)) {
            stateSystem.previous();
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
            
            UnitCard card  = (UnitCard) stagedCard;
            Unit unit = card.getUnit();
            UnitType row = UnitType.values()[rowIndex];
            board.addUnit(unit, row, unit.isFriendly());
            player.removeCardFromHand(stagedCard);
        }
        
        for (Ability ability : stagedCard.getAbilities()) {
            ability.activate(this);
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
    
    public GameBoard getBoard() {
        return board;
    }
    
    public Player getPlayer() {
        return player;
    }
}
