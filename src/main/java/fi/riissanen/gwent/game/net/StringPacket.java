package fi.riissanen.gwent.game.net;

import fi.riissanen.gwent.engine.net.Packet;

/**
 *
 * @author Daniel
 */
public class StringPacket extends Packet {
    
    private final String message;
    
    public StringPacket(int id, String message) {
        super(id);
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
}
