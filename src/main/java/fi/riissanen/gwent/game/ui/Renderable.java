package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.SpriteBatch;

/**
 * Provides a render method.
 * @author Daniel
 */
public interface Renderable {
    
    /**
     * Render method.
     * @param batch The sprite batch to render with
     */
    public void render(SpriteBatch batch);
}
