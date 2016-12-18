package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;

/**
 * An implementation of Renderable.
 * @author Daniel
 */
public class GUIComponent implements Renderable {
    
    protected final Texture[] textures;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    
    /**
     * Constructor.
     * @param textures The array of textures that this component has
     */
    public GUIComponent(Texture... textures) {
        this.textures = textures == null ? new Texture[0] : textures;
    }
    
    @Override
    public void render(SpriteBatch batch) {
        for (Texture texture : textures) {
            if (texture != null) {
                batch.draw(texture, x, y, width, height);
            }
        }
    }
    
    /**
     * Update method.
     */
    public void update() {
    }
    
    /**
     * Called when the component is destroyed.
     */
    public void destroy() {
    }
    
    /**
     * Sets the component position.
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Sets the size of the component.
     * @param width The width
     * @param height The height
     */
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
    }
    
    public float getWidth() {
        return width;
    }
    
    public float getHeight() {
        return height;
    }
}
