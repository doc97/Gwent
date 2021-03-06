package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.Color;
import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.engine.render.fonts.Font;
import fi.riissanen.gwent.engine.render.fonts.Text;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.CombatRow;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Graphical representation of combat row.
 *
 * @author Daniel
 */
public class GUIRow extends GUIComponent {

    private final Map<UnitCard, GUICard> cards = new HashMap<>();
    private final CombatRow row;
    private final TextCache cache;
    private Text strength;
    
    /**
     * Creates a GUIComponent with cards, a little like the GUI hand.
     * @param row The row that is linked with this GUIRow
     * @param texture The texture
     * @param strength The text showing the rows strength
     * @param cache The text cache where to add component texts
     */
    public GUIRow(CombatRow row, Texture texture, Text strength, TextCache cache) {
        super(texture);
        this.row = row;
        this.cache = cache;
        this.strength = strength;
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        for (UnitCard card : cards.keySet()) {
            cards.get(card).render(batch);
        }
    }
    
    @Override
    public void update() {
        // Update card information
        for (Iterator<UnitCard> it = cards.keySet().iterator(); it.hasNext();) {
            UnitCard card = it.next();
            if (!row.hasUnit(card.getUnit())) {
                cards.get(card).destroy();
                it.remove();
                continue;
            }
            cards.get(card).update();
        }
        
        // Update row strength
        int rowStr = row.getStrength();
        int rowStrength = Integer.parseInt(strength.getText());
        if (rowStr != rowStrength) {
            cache.removeText(strength);
            Color color = strength.getColor();
            Font font = strength.getFont();
            float fontSize = strength.getFontSize();
            float lineLength = strength.getMaxLineLength();

            strength = new Text(rowStr + "", font, fontSize, lineLength);;
            strength.setColor(color.getRed(), color.getGreen(), color.getBlue());
        }
        
        if (!cache.hasText(strength)) {
            cache.addText(strength);
            // Recalculate position after the text has been reloaded
            strength.setPosition(x - strength.getLineLength() - 10, y + height / 2);
        }
    }
    
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        strength.setPosition(x - strength.getLineLength() - 10, y + height / 2);
    }
    
    /**
     * Adds a GUICard, mapped to a Card key.
     * @param card The key to map the GUI card to 
     * @param guiCard The GUI component
     */
    public void addCard(UnitCard card, GUICard guiCard) {
        if (!cards.containsKey(card)) {
            guiCard.setPosition(x + 10 + cards.size() * (guiCard.width + 10),
                    y + (height - guiCard.getHeight()) / 2);
            cards.put(card, guiCard);
        }
    }
    
    /**
     * Remove a GUICard based on a Card key.
     * @param card The key
     */
    public void removeCard(UnitCard card) {
        cards.get(card).destroy();
        cards.remove(card);
        
        int i = 0;
        for (UnitCard c : cards.keySet()) {
            GUICard guiCard = cards.get(c);
            guiCard.setPosition(x + 10 + i * (guiCard.width + 10),
                    y + (height - guiCard.getHeight()) / 2);
            i++;
        }
    }
    
    public CombatRow getRow() {
        return row;
    }
}
