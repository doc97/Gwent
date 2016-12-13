package fi.riissanen.gwent.engine.input;

import java.util.ArrayList;
import java.util.List;

/**
 * Supports multiple child input listeners.
 * @author Daniel
 */
public class InputMultiplexer extends InputAdapter {

    private final List<InputListener> addListeners = new ArrayList<>();
    private final List<InputListener> remListeners = new ArrayList<>();
    private final List<InputListener> listeners = new ArrayList<>(); 
    private int invokedCount;
    
    /**
     * Initializes the multiplexer with listeners.
     * @param listeners The input listeners
     */
    public InputMultiplexer(InputListener... listeners) {
        for (InputListener l : listeners) {
            this.listeners.add(l);
        }
    }
    
    @Override
    public void update() {
        if (invokedCount == 0) {
            for (InputListener add : addListeners) {
                listeners.add(add);
            }

            for (InputListener rem : remListeners) {
                listeners.remove(rem);
            }

            addListeners.clear();
            remListeners.clear();
        }
        
        for (InputListener listener : listeners) {
            listener.update();
        }
    }
    
    @Override
    public boolean keyPressed(int key) {
        invokedCount++;
        for (InputListener listener : listeners) {
            if (listener.keyPressed(key)) {
                break;
            }
        }
        invokedCount--;
        return false;
    }
    
    @Override
    public boolean keyRepeat(int key) {
        invokedCount++;
        for (InputListener listener : listeners) {
            if (listener.keyRepeat(key)) {
                break;
            }
        }
        invokedCount--;
        return false;
    }
    
    @Override
    public boolean keyReleased(int key) {
        invokedCount++;
        for (InputListener listener : listeners) {
            if (listener.keyReleased(key)) {
                break;
            }
        }
        invokedCount--;
        return false;
    }
    
    @Override
    public boolean mousePressed(int key) {
        invokedCount++;
        for (InputListener listener : listeners) {
            if (listener.mousePressed(key)) {
                break;
            }
        }
        invokedCount--;
        return false;
    }
    
    @Override
    public boolean mouseReleased(int key) {
        invokedCount++;
        for (InputListener listener : listeners) {
            if (listener.mouseReleased(key)) {
                break;
            }
        }
        invokedCount--;
        return false;
    }

    /**
     * Queues an addition of an input listener.
     * @param listener The listener to add
     */
    public void addListener(InputListener listener) {
        addListeners.add(listener);
    }

    /**
     * Queues a removal of an input listener.
     * @param listener The listener to remove
     */
    public void removeListener(InputListener listener) {
        remListeners.add(listener);
    }

    /**
     * Clears the listeners list.
     */
    public void removeAllListeners() {
        listeners.clear();
    }
}
