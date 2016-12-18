package fi.riissanen.gwent.game.input;

import fi.riissanen.gwent.engine.Engine;
import fi.riissanen.gwent.engine.input.InputAdapter;
import fi.riissanen.gwent.engine.render.Viewport;
import fi.riissanen.gwent.game.ui.GUIComponent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Handles all GUI input.
 * @author Daniel
 */
public class GUIInput extends InputAdapter {

    private final Map<GUIComponent, GUIComponentState> states;
    private final Map<GUIComponent, List<GUIInputListener>> listeners; 
    private Viewport viewport;
    
    /**
     * Constructor.
     */
    public GUIInput() {
        states = new HashMap<>();
        listeners = new HashMap<>();
    }
    
    /**
     * Initializes viewport.
     */
    public void initialize() {
        viewport = new Viewport(0, 0,
                Engine.INSTANCE.display.getWidth(),
                Engine.INSTANCE.display.getHeight());
        Viewport batchView = Engine.INSTANCE.batch.getViewport();
        viewport.targetViewport(
                batchView.getSrcLeft(),
                batchView.getSrcBottom(),
                batchView.getSrcLeft() + batchView.getSrcWidth(),
                batchView.getSrcBottom() + batchView.getSrcHeight()
        );
    }
    
    @Override
    public boolean mouseMoved(double x, double y) {
        y = viewport.getSrcHeight() - y; // Origin is top left for input
        float tx = viewport.toTargetCoordinateX((float) x);
        float ty = viewport.toTargetCoordinateY((float) y);
        for (GUIComponent component : listeners.keySet()) {
            if (tx >= component.getX() &&
                tx <= component.getX() + component.getWidth() &&
                ty >= component.getY() &&
                ty <= component.getY() + component.getHeight()) {
                for (GUIInputListener listener : listeners.get(component)) {
                    if (listener.isEnabled()) {
                        listener.enterComponent(component);
                    }
                }
                states.get(component).isOver = true;
            } else {
                for (GUIInputListener listener : listeners.get(component)) {
                    if (listener.isEnabled()) {
                        listener.exitComponent(component);
                    }
                }
                states.get(component).isOver = false;
                states.get(component).isPressed = false;
            }
        }
        return false;
    }

    @Override
    public boolean mousePressed(int button) {
        for (Iterator<GUIComponent> it = listeners.keySet().iterator(); it.hasNext();) {
            GUIComponent component = it.next();
            if (states.get(component).isOver) {
                for (Iterator<GUIInputListener> it2 = listeners.get(component).iterator(); it2.hasNext();) {
                    GUIInputListener listener = it2.next();
                    if (listener.isEnabled()) {
                        listener.pressComponent(component, button);
                    }
                }
                states.get(component).isPressed = true;
            }
        }
        return false;
    }
    
    @Override
    public boolean mouseReleased(int button) {
        for (Iterator<GUIComponent> it = listeners.keySet().iterator(); it.hasNext();) {
            GUIComponent component = it.next();
            if (states.get(component).isOver) {
                for (Iterator<GUIInputListener> it2 = listeners.get(component).iterator(); it2.hasNext();) {
                    GUIInputListener listener = it2.next();
                    if (listener.isEnabled()) {
                        listener.releaseComponent(component, button);
                    }
                }
                states.get(component).isPressed = false;
            }
        }
        return false;
    }
    
    /**
     * Adds a listener to a component.
     * @param component The component
     * @param listener The listener
     */
    public void addListener(GUIComponent component, GUIInputListener listener) {
        List<GUIInputListener> list = listeners.get(component);
        if (list == null) {
            list = new ArrayList<>();
            listeners.put(component, list);
            states.put(component, new GUIComponentState());
        }
        list.add(listener);
        
    }
    
    /**
     * Removes a listener from a component.
     * @param component The component
     * @param listener The listener
     */
    public void removeListener(GUIComponent component, GUIInputListener listener) {
        List<GUIInputListener> list = listeners.get(component);
        if (list != null) {
            list.remove(listener);
            if (list.isEmpty()) {
                listeners.remove(component);
                states.remove(component);
            }
        }
    }
    
    /**
     * Removes the listener from all components.
     * @param listener The listener
     */
    public void removeListenerFromAll(GUIInputListener listener) {
        for (GUIComponent component : listeners.keySet()) {
            listeners.get(component).remove(listener);
        }
    }
    
    private class GUIComponentState {
        public boolean isOver;
        public boolean isPressed;
    }
}
