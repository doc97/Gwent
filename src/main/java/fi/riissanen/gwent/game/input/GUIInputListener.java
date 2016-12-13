package fi.riissanen.gwent.game.input;

import fi.riissanen.gwent.game.ui.GUIComponent;

/**
 * Listens for GUI events.
 * @author Daniel
 */
public interface GUIInputListener {
    
    /**
     * Called when the mouse has entered the component.
     * @param component The component
     */
    public void enterComponent(GUIComponent component);
    
    /**
     * Called when the mouse has exited the component.
     * @param component The component
     */
    public void exitComponent(GUIComponent component);
    
    /**
     * Called when a mouse button is pressed down over the component.
     * @param component The component
     * @param button The mouse button
     */
    public void pressComponent(GUIComponent component, int button);
    
    /**
     * Called when a mouse button is released over the component.
     * 
     * <p>
     * It represents a click, a button press and release without
     * the mouse leaving the component.
     * @param component
     * @param button The mouse button
     */
    public void releaseComponent(GUIComponent component, int button);
    
    /**
     * Used to disable the listener.
     */
    public void disable();
    
    /**
     * Used to enable the listener.
     */
    public void enable();
    
    /**
     * Checks the if the listener is enabled.
     * @return True if the listener is enabled
     */
    public boolean isEnabled();
}
