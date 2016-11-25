package fi.riissanen.gwent.game.events;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
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
        for (EventListener listener : listeners.get(event.getClass())) {
            listener.process(event);
        }
    }
    
    public void clear() {
        events.clear();
    }
    
    public void setListeners(Class<? extends Event> clazz, List<EventListener> listeners) {
        this.listeners.put(clazz, listeners);
    }
}
