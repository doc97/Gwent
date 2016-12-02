package fi.riissanen.gwent.engine.net;

import java.io.Serializable;

/**
 * A packet sent over a socket connection.
 * @author Daniel
 */
public abstract class Packet implements Serializable {

    private int senderID;

    /**
     * Creates a packet with a sender id.
     * @param senderID The ID of the sender
     */
    public Packet(int senderID) {
        this.senderID = senderID;
    }

    public void setSenderID(int id) {
        senderID = id;
    }

    public int getSenderID() {
        return senderID;
    }
}
