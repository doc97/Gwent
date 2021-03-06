package fi.riissanen.gwent.game;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.Logger;
import fi.riissanen.gwent.engine.Logger.LogLevel;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.Deck;
import fi.riissanen.gwent.game.cards.Hand;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.Unit;
import fi.riissanen.gwent.game.events.CardDiscardedEvent;
import fi.riissanen.gwent.game.events.DrawCardEvent;
import fi.riissanen.gwent.game.factions.Faction;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the player playing the game.
 * @author Daniel
 */
public class Player {

    private final Gwent game;
    private final List<Card> discardPile;
    private final Random rng;
    private final Deck deck;
    private final Hand hand;    
    private final boolean friendly;
    private boolean inTurn;
    private Faction faction;
    
    /**
     * Constructor.
     * @param game The game
     * @param friendly True if the player is friendly
     */
    public Player(Gwent game, boolean friendly) {
        this.game = game;
        this.friendly = friendly;
        rng = new Random();
        deck = new Deck();
        hand = new Hand();
        discardPile = new ArrayList<>();
    }
    
    /**
     * Assigns a deck to the player.
     *
     * <p>
     * The owner of the cards is set to this player
     * @param deck The deck to assign to player
     */
    public void setDeck(Deck deck) {
        this.deck.clearCards();
        for (int i = 0; i < deck.getCardCount(); i++) {
            Card card = deck.getCard(i);
            card.setOwner(this);
            this.deck.addCard(card);
        }
    }
    
    /**
     * Removes a card from the player's hand.
     * @param card The card to remove
     */
    public void removeCardFromHand(Card card) {
        hand.removeCard(card);
    }
    
    /**
     * Removes a card from the player's deck.
     * @param card The card to remove
     */
    public void removeCardFromDeck(Card card) {
        deck.removeCard(card);
    }
    
    /**
     * Adds a card to the discard pile.
     * @param card The card to discard
     */
    public void discardCard(Card card) {
        discardPile.add(card);
        if (game != null && game.getEventSystem() != null) {
            game.getEventSystem().register(new CardDiscardedEvent(card));
        }
    }
    
    /**
     * Pops a card from the discard pile.
     * @param index The index of the card
     * @return The popped card if the index is valid
     */
    public Card popDiscardCard(int index) {
        if (index >= 0 && index < discardPile.size()) {
            return discardPile.remove(index);
        }
        return null;
    }
    
    public void setFaction(Faction faction) {
        this.faction = faction;
    }
    
    public void setInTurn(boolean turn) {
        inTurn = turn;
    }
    
    /**
     * Draws cards.
     * @param count The amount of cards to draw 
     */
    public void drawCards(int count) {
        for (int i = 0; i < count; i++) {
            drawCard();
        }
    }
    
    /**
     * Draws one card.
     */
    public void drawCard() {
        if (deck.getCardCount() > 0) {
            int index = rng.nextInt(deck.getCardCount());
            Card card = deck.getCard(index);
            deck.removeCard(card);
            hand.addCard(card);
            game.getEventSystem().register(new DrawCardEvent(card, friendly));
        }
    }
    
    /**
     * Retrieves a list of cards from the deck with a specific name.
     * @param name The name to search for
     * @return The list of cards
     */
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
    
    /**
     * Redraws a card.
     * @param card The card to redraw
     */
    public void redrawCard(Card card) {
        if (!hand.removeCard(card)) {
            throw new IllegalArgumentException(
                    "Cannot discard a card that is not in hand");
        }
        
        drawCard();
        deck.addCard(card);
    }
    
    /**
     * Returns a card from the hand by it's index.
     * @param index The index of the card
     * @return The card, or null if the index is out of bounds
     */
    public Card getHandCard(int index) {
        return hand.getCard(index);
    }
    
    /**
     * Returns a card from the deck by it's index.
     * @param index The index of the card
     * @return The card, or null if the index is out of bounds
     */
    public Card getDeckCard(int index) {
        return deck.getCard(index);
    }
    
    public Faction getFaction() {
        return faction;
    }
    
    public int getDiscardPileSize() {
        return discardPile.size();
    }
    
    public int getDeckSize() {
        return deck.getCardCount();
    }
    
    public int getHandSize() {
        return hand.getCardCount();
    }
    
    public boolean isInTurn() {
        return inTurn;
    }
    
    public boolean isFriendly() {
        return friendly;
    }
    
    /**
     * Prints the status of the hand to the engine long.
     */
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
    
    /**
     * Prints the status of the deck to the engine log.
     */
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
}
