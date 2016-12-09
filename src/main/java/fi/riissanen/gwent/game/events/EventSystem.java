package fi.riissanen.gwent.game.events;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Handles event distribution and listeners
 * @author Daniel
 */
public class EventSystem {

    private final Map<Class<? extends Event>, List<EventListener>> listeners = new HashMap<>();
    private final Queue<Event> events = new ArrayDeque<>();
    
    public void register(Event event) {
        events.add(event);
    }
    
    public void update() {
        Event e;
        while ((e = events.poll()) != null) {
            handleEvent(e);
        }
    }
    
    private void handleEvent(Event event) {
        List<EventListener> eventListeners = listeners.get(event.getClass());
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                listener.process(event);
            }
        }
    }
    
    public void clear() {
        events.clear();
    }
    
    public void addListener(Class<? extends Event> clazz, EventListener listener) {
        List<EventListener> list = listeners.get(clazz);
        if (list != null) {
            listeners.get(clazz).add(listener);
        } else {
            list = new ArrayList<>();
            list.add(listener);
            listeners.put(clazz, list);
        }
    }
    
    public void removeListener(Class<? extends Event> clazz, EventListener listener) {
        List<EventListener> list = listeners.get(clazz);
        if (list != null) {
            list.remove(listener);
            if (listeners.isEmpty()) {
                this.listeners.remove(clazz);
            }
        }
    }
    
    public void addListeners(Class<? extends Event> clazz, List<EventListener> listeners) {
        if (this.listeners.containsKey(clazz)) {
            this.listeners.get(clazz).addAll(listeners);
        } else {
            this.listeners.put(clazz, listeners);
        }
    }
    
    public int getEventsPendingCount() {
        return events.size();
    }
    
    public int getEventListenerCount(Class<? extends Event> clazz) {
        return listeners.get(clazz).size();
    }
}
