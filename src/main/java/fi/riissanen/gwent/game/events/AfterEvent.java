package fi.riissanen.gwent.game.events;

/**
 * Called after another event has been processed.
 * @author Daniel
 */
public class AfterEvent implements Event {

    private final Class<? extends Event> event;
    
    public AfterEvent(Class<? extends Event> event) {
        this.event = event;
    }
    
    public Class<? extends Event> getEvent() {
        return event;
    }
}
