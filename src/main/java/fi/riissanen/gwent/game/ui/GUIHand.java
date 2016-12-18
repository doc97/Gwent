package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.game.cards.Card;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Graphical representation of hand.
 * @author Daniel
 */
public class GUIHand extends GUIComponent {

    private final Map<Card, GUICard> cards = new LinkedHashMap<>();
    
    /**
     * Creates a GUI component containing GUI cards.
     * @param texture The texture
     */
    public GUIHand(Texture texture) {
        super(texture);
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
    
    /**
     * Gets the {@link GUICard} linked to a Card.
     * @param card The card that is linked
     * @return The GUICard
     */
    public GUICard getGUICard(Card card) {
        return cards.get(card);
    }
}
