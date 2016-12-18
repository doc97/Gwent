package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.Color;
import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;

/**
 * A crystal.
 * @author Daniel
 */
public class GUICrystal extends GUIComponent {

    private final Color color = new Color(1, 1, 1, 1);

    public GUICrystal(Texture texture) {
        super(texture);
    }
    
    @Override
    public void render(SpriteBatch batch) {
        Color oldColor = batch.getColor();
        batch.setColor(color);
        super.render(batch);
        batch.setColor(oldColor);
    }
    
    public void setColor(Color color) {
        this.color.set(color);
    }
    
    public void setColor(float r, float g, float b, float a) {
        color.set(r, g, b, a);
    }
}
