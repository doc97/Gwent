package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.game.cards.Card;
import java.util.HashMap;
import java.util.Map;

/**
 * Graphical representation of hand.
 * @author Daniel
 */
public class GUIHand extends GUIComponent {

    private final Map<Card, GUICard> cards = new HashMap<>();
    private final TextCache cache;
    
    /**
     * Creates a GUI component containing GUI cards.
     * @param texture The texture
     * @param cache The text cache to add component texts to
     */
    public GUIHand(Texture texture, TextCache cache) {
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
    
    @Override
    public void update() {
        for (Card card : cards.keySet()) {
            cards.get(card).update();
        }
    }
    
    /**
     * Adds a GUICard, mapped to a Card key.
     * @param card The key to map the GUI card to 
     * @param guiCard The GUI component
     */
    public void addCard(Card card, GUICard guiCard) {
        if (!cards.containsKey(card)) {
            guiCard.setPosition(x + 10 + cards.size() * (guiCard.getWidth() + 10),
                    y + (height - guiCard.getHeight()) / 2);
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
            guiCard.setPosition(x + 10 + i * (guiCard.width + 10),
                    y + (height - guiCard.getHeight()) / 2);
            i++;
        }
    }
    
    public GUICard getGUICard(Card card) {
        return cards.get(card);
    }
}
