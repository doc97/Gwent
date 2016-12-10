package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.SpriteBatch;
import java.util.List;

/**
 * Renders the graphical user interface.
 * @author Daniel
 */
public class GUIRenderer {
    
    /**
     * Renders a list of GUIComponents.
     * @param batch The sprite batch to render with
     * @param components The components to render
     */
    public void render(SpriteBatch batch, List<GUIComponent> components) {
        for (GUIComponent component : components) {
            component.render(batch);
        }
    }
}
