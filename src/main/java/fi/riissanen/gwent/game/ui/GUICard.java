package fi.riissanen.gwent.game.ui;

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
    private final Text text;
    
    public GUICard(Text text, Texture texture) {
        super(texture);
        this.text = text;
        text.setColor(0, 0, 0);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
    }
    
    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        if (hasText()) {
            float lineHeight = text.getFont().getLineHeight() * text.getFontSize();
            text.setPosition(this.x + 2, this.y + height - lineHeight);
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
    
    public boolean hasText() {
        return text != null;
    }
    
    public Text getText() {
        return text;
    }
}
