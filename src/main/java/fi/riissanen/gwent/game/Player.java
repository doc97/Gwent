package fi.riissanen.gwent.game;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.Logger;
import fi.riissanen.gwent.engine.Logger.LogLevel;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.Deck;
import fi.riissanen.gwent.game.cards.Hand;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.factions.Faction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the player playing the game
 * @author Daniel
 */
public class Player {

    private final List<Card> discardPile;
    private final Random rng;
    private final Deck deck;
    private final Hand hand;    
    private final boolean friendly;
    private boolean inTurn;
    private Faction faction;
    
    public Player(Deck deck, boolean friendly) {
        this.deck = deck;
        this.friendly = friendly;
        rng = new Random();
        hand = new Hand();
        discardPile = new ArrayList<>();
    }
    
    public void removeCardFromHand(Card card) {
        hand.removeCard(card);
    }
    
    public void removeCardFromDeck(Card card) {
        deck.removeCard(card);
    }
    
    public void discardCard(Card card) {
        discardPile.add(card);
    }
    
    public Card popDiscardCard(int index) {
        if (index >= 0 && index < discardPile.size()) {
            return discardPile.remove(index);
        }
        return null;
    }
    
    public int getDiscardPileSize() {
        return discardPile.size();
    }
    
    public void setFaction(Faction faction) {
        this.faction = faction;
    }
    
    public void setInTurn(boolean turn) {
        inTurn = turn;
    }
    
    public void drawCards(int count) {
        for (int i = 0; i < count; i++) {
            drawCard();
        }
    }
    
    public void drawCard() {
        int cardsLeft = deck.getCardCount();
        if (cardsLeft > 0) {
            int index = rng.nextInt(deck.getCardCount());
            Card card = deck.getCard(index);
            deck.removeCard(card);
            hand.addCard(card);
        }
    }
    
    public List<Card> getDeckCardsByName(String name) {
        List<Card> nameCards = new ArrayList<>();
        for (int i = 0; i < deck.getCardCount(); i++) {
            Card card = deck.getCard(i);
            if (card.getName().equals(name)) {
                nameCards.add(card);
            }
        }
        return nameCards;
    }
    
    public void redrawCard(Card card) {
        if (!hand.containsCard(card)) {
            throw new IllegalArgumentException(
                    "Cannot discard a card that is not in hand");
        }
        
        drawCard();
        deck.addCard(card);
    }
    
    public Card getCard(int index) {
        return hand.getCard(index);
    }
    
    public Faction getFaction() {
        return faction;
    }
    
    public void handStatus() {
        for (int i = 0; i < hand.getCardCount(); i++) {
            Card card = hand.getCard(i);
            if (card instanceof UnitCard) {
                Unit unit = ((UnitCard) card).getUnit();
                Engine.INSTANCE.log.write(LogLevel.INFO,
                        "[" + i + "]: " + unit.toString());
            }
        }
    }
    
    public void deckStatus() {
        StringBuilder str = new StringBuilder(" | ");
        for (int i = 0; i < deck.getCardCount(); i++) {
            Card card = hand.getCard(i);
            if (card instanceof UnitCard) {
                str.append(((UnitCard) card).toString()).append(" | ");
            }
            
            if (i != 0 && i % 5 == 0) {
                str.append("\n");
            }
        }
        Engine.INSTANCE.log.write(Logger.LogLevel.INFO, str.toString());
    }
    
    public boolean isInTurn() {
        return inTurn;
    }
    
    public boolean isFriendly() {
        return friendly;
    }
}
