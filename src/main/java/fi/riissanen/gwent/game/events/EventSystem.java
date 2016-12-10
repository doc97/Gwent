package fi.riissanen.gwent.game.events;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Handles event distribution and listeners.
 * @author Daniel
 */
public class EventSystem {

    private final Map<Class<? extends Event>, List<EventListener>> listeners = new HashMap<>();
    private final Queue<Event> events = new ArrayDeque<>();
    
    /**
     * Registers an event, adds it to the queue of events yet to process.
     * @param event The event to register
     */
    public void register(Event event) {
        events.add(event);
    }
    
    /**
     * Handles registered events.
     */
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
    
    /**
     * Removes the registered events from the queue.
     */
    public void clear() {
        events.clear();
    }
    
    /**
     * Adds an event listener to an event.
     * @param clazz The event
     * @param listener The event listener
     */
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
    
    /**
     * Removes an event listener.
     * @param clazz The class of the event that the listener no longer will listen to
     * @param listener The event listener
     */
    public void removeListener(Class<? extends Event> clazz, EventListener listener) {
        List<EventListener> list = listeners.get(clazz);
        if (list != null) {
            list.remove(listener);
            if (listeners.isEmpty()) {
                this.listeners.remove(clazz);
            }
        }
    }
    
    /**
     * Add a list of event listeners to listen for an event.
     * @param clazz The class of the event
     * @param listeners The list of listeners
     */
    public void addListeners(Class<? extends Event> clazz, List<EventListener> listeners) {
        if (this.listeners.containsKey(clazz)) {
            this.listeners.get(clazz).addAll(listeners);
        } else {
            this.listeners.put(clazz, listeners);
        }
    }
    
    /**
     * Gets the number of events that are waiting to get processed.
     * @return The event count
     */
    public int getEventsPendingCount() {
        return events.size();
    }
    
    /**
     * Gets the number of event listeners for a certain event.
     * @param clazz The event
     * @return The event listener count
     */
    public int getEventListenerCount(Class<? extends Event> clazz) {
        return listeners.get(clazz).size();
    }
}
