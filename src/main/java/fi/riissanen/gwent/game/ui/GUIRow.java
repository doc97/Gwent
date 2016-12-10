package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.game.cards.Card;
import java.util.HashMap;
import java.util.Map;

/**
 * Graphical representation of combat row.
 *
 * @author Daniel
 */
public class GUIRow extends GUIComponent {

    private final Map<Card, GUICard> cards = new HashMap<>();
    private final TextCache cache;

    /**
     * Creates a GUIComponent with cards, a little like the GUI hand.
     * @param texture The texture
     * @param cache The text cache where to add component texts
     */
    public GUIRow(Texture texture, TextCache cache) {
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
            guiCard.setPosition(x + 10 + cards.size() * (guiCard.width + 10),
                    y + (height - guiCard.getHeight()) / 2);
            cards.put(card, guiCard);
            cache.addText(guiCard.getText());
        }
    }
    
    /**
     * Remove a GUICard based on a Card key.
     * @param card The key
     */
    public void removeCard(Card card) {
        cache.removeText(cards.get(card).getText());
        cards.remove(card);
        
        int i = 0;
        for (Card c : cards.keySet()) {
            GUICard guiCard = cards.get(c);
            guiCard.setPosition(x + 10 + i * (guiCard.width + 10),
                    y + (height - guiCard.getHeight()) / 2);
            i++;
        }
    }
}
