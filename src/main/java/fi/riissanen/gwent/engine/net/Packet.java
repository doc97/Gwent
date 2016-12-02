package fi.riissanen.gwent.engine.net;

import java.io.Serializable;

/**
 * A packet sent over a socket connection
 * @author Daniel
 */
public abstract class Packet implements Serializable {

    private int senderID;

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
