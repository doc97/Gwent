package fi.riissanen.gwent.game;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.Logger;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.Deck;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.GameBoard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import java.util.ArrayList;
import java.util.List;

/**
 * In charge of handling the flow of the game
 *
 * @author Daniel
 */
public class GameSystem {

    private GameBoard board;
    private Player player;
    private Card stagedCard;

    public boolean initialize() {
        Deck deck = createDeck();
        if (!deck.validate()) {
            return false;
        }
        
        board = new GameBoard();
        player = new Player(createDeck());
        player.drawCards(10);
        player.setInTurn(true);
        return true;
    }

    /**
     * Temporary method for testing purposes.
     *
     * @return A constructed deck, or null if it is not valid for some reason
     */
    private Deck createDeck() {
        Deck tempDeck = new Deck();
        List<UnitType> mType = new ArrayList<>();
        mType.add(UnitType.MELEE);
        List<UnitType> rType = new ArrayList<>();
        rType.add(UnitType.RANGED);
        List<UnitType> aTypes = new ArrayList<>();
        aTypes.add(UnitType.MELEE);
        aTypes.add(UnitType.RANGED);
        
        Unit mUnit = new Unit(mType, 2);
        Unit rUnit = new Unit(rType, 1);
        Unit agile = new Unit(aTypes, 3);
        
        for (int i = 0; i < Deck.MIN_CARDS; i++) {
            if (i < Deck.MIN_CARDS / 3) {
                tempDeck.addCard(new UnitCard(mUnit));
            } else if (i < Deck.MIN_CARDS / 2) {
                tempDeck.addCard(new UnitCard(rUnit));
            } else {
                tempDeck.addCard(new UnitCard(agile));
            }
        }
        return tempDeck.validate() ? tempDeck : null;
    }
    
    public void stageCard(Card card) {
        stagedCard = card;
    }
    
    public void unstageCard() {
        stagedCard = null;
    }
    
    public void playCard(int rowIndex) {
        if (!cardIsStaged() || noPlayerInTurn()) {
            unstageCard();
            return;
        }
        
        if (stagedCard instanceof UnitCard) {
            if (rowIndex == -1) {
                throw new IllegalStateException("You must first pick a row");
            }
            
            Unit unit = ((UnitCard) stagedCard).getUnit();
            UnitType row = UnitType.values()[rowIndex];
            board.addUnit(unit, row, player.isInTurn());
            player.removeCardFromHand(stagedCard);
        }
        unstageCard();
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
