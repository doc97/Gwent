package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.game.cards.Card;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Graphical representation of the discard pile.
 * @author Daniel
 */
public class GUIDiscardPile extends GUIComponent {

    private final Map<Card, GUICard> cards = new LinkedHashMap<>();
    private final TextCache cache;
    
    public GUIDiscardPile(Texture texture, TextCache cache) {
        super(texture);
        this.cache = cache;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        for (Card card : cards.keySet()) {
            cards.get(card).render(batch);
        }
    }
    
    /**
     * Adds a GUICard, mapped to a Card key.
     * @param card The key to map the GUI card to 
     * @param guiCard The GUI component
     */
    public void addCard(Card card, GUICard guiCard) {
        if (!cards.containsKey(card)) {
            if (cards.size() < 4) {
                guiCard.setPosition(x + (cards.size() + 1) * 2,
                    y + height - GUICard.HEIGHT - (cards.size() + 1) * 3);
            } else {
                guiCard.setPosition(x + 5 * 2,
                    y + height - GUICard.HEIGHT - 5 * 3);
            }
            
            cards.put(card, guiCard);
        }
    }
    
    /**
     * Remove a GUICard based on a Card key.
     * @param card The key
     */
    public void removeCard(Card card) {
        cards.get(card).destroy();
        cards.remove(card);
        
        int i = 0;
        for (Card c : cards.keySet()) {
            GUICard guiCard = cards.get(c);
            if (i < 4) {
                guiCard.setPosition(x + (i + 1) * 2, y - (i + 1) * 3);
            } else {
                guiCard.setPosition(x + 5 * 2, y - 5 * 3);
            }
            i++;
        }
    }
    
    public GUICard getGUICard(Card card) {
        return cards.get(card);
    }
}
