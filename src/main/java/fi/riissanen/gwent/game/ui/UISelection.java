package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;

/**
 * A selection on the GameBoard.
 * @author Daniel
 */
public class UISelection implements Renderable {
    
    private final Texture texture;
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean active;
    
    public UISelection(Texture texture) {
        this.texture = texture;
    }
    
    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y, width, height);
    }
    
    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public boolean isActive() {
        return active;
    }
}
