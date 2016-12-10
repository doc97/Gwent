package fi.riissanen.gwent.game.net;

import fi.riissanen.gwent.engine.net.Packet;

/**
 * A {@code Packet} containing a String.
 * @author Daniel
 */
public class StringPacket extends Packet {
    
    private final String message;
    
    /**
     * Creates a packet with a sender id and message.
     * @param id The ID of the sender
     * @param message The message
     */
    public StringPacket(int id, String message) {
        super(id);
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
