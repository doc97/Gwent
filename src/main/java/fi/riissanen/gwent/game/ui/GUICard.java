package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.math.Vector2f;
import fi.riissanen.gwent.engine.render.Color;
import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;
import fi.riissanen.gwent.engine.render.fonts.Font;
import fi.riissanen.gwent.engine.render.fonts.Text;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;

/**
 * Graphical representation of card.
 * @author Daniel
 */
public class GUICard extends GUIComponent {

    public static final int WIDTH = 80;
    public static final int HEIGHT = 120;
    private final Card card;
    private final Texture faction;
    private final Color factionColor = new Color(1, 1, 1, 1);
    private final TextCache cache;
    private Text text;
    
    /**
     * Creates a GUI component with a text.
     * @param card The card that is linked with this GUICard
     * @param text The text containing the strength
     * @param cache The text cache where to add component texts
     * @param base The base card texture
     * @param faction The faction specific texture
     * @param factionColor The color to draw the faction texture with
     */
    public GUICard(Card card, Text text, TextCache cache,
            Texture base, Texture faction, Color factionColor) {
        super(base);
        this.card = card;
        this.text = text;
        this.cache = cache;
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
    public void update() {
        if (card instanceof UnitCard) {
            int cardStr = ((UnitCard) card).getUnit().getStrength();
            int cardStrength = Integer.parseInt(text.getText());
            if (cardStr != cardStrength) {
                cache.removeText(text);
                Vector2f pos = text.getPosition();
                Color color = text.getColor();
                Font font = text.getFont();
                float fontSize = text.getFontSize();
                float lineLength = text.getMaxLineLength();
                
                text = new Text(cardStr + "", font, fontSize, lineLength);
                text.setPosition(pos.x, pos.y);
                text.setColor(color.getRed(), color.getGreen(), color.getBlue());
            }
        }
        if (!cache.hasText(text)) {
            cache.addText(text);
        }
    }
    
    @Override
    public void destroy() {
        cache.removeText(text);
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
            text.setFontSize(text.getFontSize() * height / GUICard.HEIGHT);
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
    
    public Card getCard() {
        return card;
    }
    
    public Text getText() {
        return text;
    }
}
