package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.SpriteBatch;
import fi.riissanen.gwent.engine.render.Texture;

/**
 * A selection on the GameBoard.
 * @author Daniel
 */
public class UISelection extends GUIComponent {
    
    private boolean active;

    /**
     * Creates a GUIComponent that can be activated and deactivated.
     * @param texture The texture
     */
    public UISelection(Texture texture) {
        super(texture);
    }
    
    @Override
    public void render(SpriteBatch batch) {
        if (active) {
            super.render(batch);
        }
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public boolean isActive() {
        return active;
    }
}
