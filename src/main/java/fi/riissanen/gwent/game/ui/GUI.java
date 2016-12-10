package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.engine.render.SpriteBatch;
import java.util.ArrayList;
import java.util.List;

/**
 * Master of the GUI.
 * @author Daniel
 */
public class GUI {

    private final GUIRenderer renderer;
    private final List<GUIComponent> components;
    
    /**
     * Constructor.
     */
    public GUI() {
        components = new ArrayList<>();
        renderer = new GUIRenderer();
    }
    
    /**
     * Renders the GUI components.
     * @param batch The sprite batch to render with
     */
    public void render(SpriteBatch batch) {
        batch.begin();
        renderer.render(batch, components);
        batch.end();
    }
    
    /**
     * Add a GUI component.
     * @param component The component to add
     */
    public void addComponent(GUIComponent component) {
        if (component != null) {
            components.add(component);
        }
    }
    
    /**
     * Remove a GUI component.
     * @param component The component to remove
     */
    public void removeComponent(GUIComponent component) {
        components.remove(component);
    }
}
