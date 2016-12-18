package fi.riissanen.gwent.game.input;

import fi.riissanen.gwent.game.ui.GUIComponent;

/**
 * Implements an {@link GUIInputListener}.
 * @author Daniel
 */
public class GUIInputAdapter implements GUIInputListener {
    
    private boolean enabled = true; // Default value is true
    
    @Override
    public void enterComponent(GUIComponent component) {
    }

    @Override
    public void exitComponent(GUIComponent component) {
    }

    @Override
    public void pressComponent(GUIComponent component, int button) {
    }

    @Override
    public void releaseComponent(GUIComponent component, int button) {
    }
    
    @Override
    public void disable() {
        enabled = false;
    }
    
    @Override
    public void enable() {
        enabled = true;
    }
    
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
