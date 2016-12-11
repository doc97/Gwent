package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.Color;
import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.engine.render.fonts.Text;

/**
 * Graphical representation of card.
 * @author Daniel
 */
public class GUICard extends GUIComponent {

    public static final int WIDTH = 80;
    public static final int HEIGHT = 125;
    private final Texture faction;
    private final Color factionColor = new Color(1, 1, 1, 1);
    private Text text;
    
    /**
     * Creates a GUI component with a text.
     * @param text The text containing the strength
     * @param base The base card texture
     * @param faction The faction specific texture
     * @param factionColor The color to draw the faction texture with
     */
    public GUICard(Text text, Texture base, Texture faction, Color factionColor) {
        super(base);
        this.text = text;
        this.faction = faction;
        this.factionColor.set(factionColor);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if (faction != null) {
            Color oldColor = batch.getColor();
            batch.setColor(factionColor);
            batch.draw(faction, x, y, width, height);
            batch.setColor(oldColor);
        }
    }
    
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (hasText()) {
            text.setPosition(this.x + width * 3 / 24f, this.y + height * 39 / 48f);
        }
    }
    
    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        if (hasText()) {
            float lineHeight = text.getFont().getLineHeight() * text.getFontSize();
            text.setPosition(this.x + 2, this.y + height - lineHeight);
        }
    }
    
    /**
     * Sets the text for this GUICard.
     * @param text The text
     */
    public void setText(Text text) {
        this.text = text;
    }
    
    /**
     * Checks if the card has a text.
     * @return True if text is not null
     */
    public boolean hasText() {
        return text != null;
    }
    
    public Text getText() {
        return text;
    }
}
