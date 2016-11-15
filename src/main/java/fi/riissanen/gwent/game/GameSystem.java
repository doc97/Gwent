package fi.riissanen.gwent.game;

import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.Deck;
import fi.riissanen.gwent.game.cards.Hand;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.GameBoard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.combat.UnitType;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * In charge of handling the flow of the game
 *
 * @author Daniel
 */
public class GameSystem {

    private GameBoard board;
    private Deck deck;
    private Hand hand;

    public boolean initialize() {
        board = new GameBoard();
        deck = createDeck();
        hand = createHand(deck);
        return hand != null;
    }

    /**
     * Temporary method for testing purposes.
     *
     * @return A constructed deck, or null if it is not valid for some reason
     */
    private Deck createDeck() {
        Deck tempDeck = new Deck();
        List<UnitType> types = new ArrayList<>();
        types.add(UnitType.MELEE);
        Unit unit = new Unit(types, 2);
        for (int i = 0; i < Deck.MIN_CARDS; i++) {
            tempDeck.addCard(new UnitCard(unit));
        }
        return tempDeck.validate() ? tempDeck : null;
    }

    /**
     * A temporary method for testing purposes. Hand creation, drawing from the
     * deck, will change and is for now only a private method.
     *
     * @param deck The deck from which to create the hand
     * @return A constructed hand, or null if not valid for some reason.
     */
    private Hand createHand(Deck deck) {
        if (deck == null || !deck.validate()) {
            return null;
        }

        
        Hand tempHand = new Hand();
        Random random = new Random();
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < deck.getCardCount(); i++) {
            indices.add(i);
        }

        for (int i = 0; i < Hand.STARTING_HAND_CARD_COUNT; i++) {
            int randIndex = random.nextInt(indices.size());
            int cardIndex = indices.get(randIndex);
            indices.remove(randIndex);

            Card card = deck.getCard(cardIndex);
            tempHand.addCard(card);
        }

        return tempHand.validateStartingHand() ? tempHand : null;
    }
    
    public void playCard(Hand hand, Card card, UnitType row, boolean friendly) {
        if (!hand.removeCard(card)) {
            return;
        }
        
        if (card instanceof UnitCard) {
            board.addUnit(((UnitCard) card).getUnit(), row, friendly);
        }
    }
    
    public GameBoard getBoard() {
        return board;
    }
    
    public Hand getHand() {
        return hand;
    }
}
