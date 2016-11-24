package fi.riissanen.gwent.game;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.Logger;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.GameBoard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;

/**
 * In charge of handling the flow of the game
 *
 * @author Daniel
 */
public class GameSystem {

    private GameBoard board;
    private Player player;
    private Card stagedCard;

    public void initialize(Player player) {
        board = new GameBoard();
        this.player = player;
        this.player.drawCards(10);
        this.player.setInTurn(true);
    }

    public void stageCard(Card card) {
        stagedCard = card;
    }
    
    public void unstageCard() {
        stagedCard = null;
    }
    
    public boolean playCard(int rowIndex) {
        if (!cardIsStaged() || noPlayerInTurn()) {
            unstageCard();
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
        unstageCard();
        return true;
    }
    
    public void status() {
        String staged = cardIsStaged() ? stagedCard.toString() : "None";
        Engine.INSTANCE.log.write(Logger.LogLevel.INFO, "Staged card: " + staged);
        board.status();
    }
    
    private boolean noPlayerInTurn() {
        return !player.isInTurn();
    }
    
    public boolean cardIsStaged() {
        return stagedCard != null;
    }
    
    public GameBoard getBoard() {
        return board;
    }
    
    public Player getPlayer() {
        return player;
    }
}
