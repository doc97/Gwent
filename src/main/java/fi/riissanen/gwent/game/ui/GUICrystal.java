package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.Color;
import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;

/**
 * A round crystal.
 * @author Daniel
 */
public class GUICrystal extends GUIComponent {

    private final Color color = new Color(1, 1, 1, 1);

    /**
     * Creates a {@link GUIComponent}.
     * @param texture The texture
     */
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
    
    /**
     * Sets the color of the crystal.
     * @param color The color
     */
    public void setColor(Color color) {
        this.color.set(color);
    }
    
    /**
     * Sets the color components of the crystal.
     * @param r The red component
     * @param g The green component
     * @param b The blue component
     * @param a The alpha component
     */
    public void setColor(float r, float g, float b, float a) {
        color.set(r, g, b, a);
    }
}
