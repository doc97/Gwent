package fi.riissanen.gwent.game.events;

/**
 * An abstract {@link Event} listener
 * 
 * <p>
 * Must be set as a listener for an event in {@link EventSystem}
 * to receive events.
 * @author Daniel
 */
public interface EventListener {
    public void process(Event event);
}
