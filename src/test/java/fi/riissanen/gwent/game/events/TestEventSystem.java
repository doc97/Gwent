package fi.riissanen.gwent.game.events;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Daniel
 */
public class TestEventSystem {

    private final EventSystem system = new EventSystem();
    
    @Test
    public void testRegister() {
        system.register(new Event() {});
        system.register(new Event() {});
        assertEquals(2, system.getEventsPendingCount());
    }
    
    @Test
    public void testUpdate() {
        MockEvent event = new MockEvent();
        List<EventListener> listeners = new ArrayList<>();
        listeners.add(new MockEventListener());
        system.setListeners(MockEvent.class, listeners);
        system.register(event);
        system.update();
        assertEquals(1, event.getValue());
    }
    
    @Test
    public void testUpdateNoListener() {
        MockEvent event = new MockEvent();
        system.register(event);
        system.update();
        assertEquals(0, event.getValue());
    }
    
    @Test
    public void testUpdateEventCount() {
        system.register(new Event() {});
        system.register(new Event() {});
        system.update();
        assertEquals(0, system.getEventsPendingCount());
    }
    
    @Test
    public void testClear() {
        system.register(new Event() {});
        system.clear();
        assertEquals(0, system.getEventsPendingCount());
    }
    
    @Test
    public void testSetListenersNone() {
        List<EventListener> listeners = new ArrayList<>();
        listeners.add(new MockEventListener());
        system.setListeners(MockEvent.class, listeners);
        assertEquals(1, system.getEventListenerCount(MockEvent.class));
    }
    
    public class MockEvent implements Event {
        private int value;
        
        public void setValue(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
    }
    
    public class MockEventListener implements EventListener {
        @Override
        public void process(Event event) {
            ((MockEvent) event).setValue(1);
        }
    }
}
