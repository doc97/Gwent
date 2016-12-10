package fi.riissanen.gwent.game.events;

/**
 * An abstract {@link Event} listener.
 * 
 * <p>
 * Must be set as a listener for an event in {@link EventSystem}
 * to receive events.
 * @author Daniel
 */
public interface EventListener {
    
    /**
     * Processes the incoming event.
     * @param event The event
     */
    public void process(Event event);
}
