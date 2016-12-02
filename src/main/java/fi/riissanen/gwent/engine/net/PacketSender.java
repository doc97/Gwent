package fi.riissanen.gwent.engine.net;

import java.util.Queue;

/**
 * Interface used to send packets with.
 * 
 * <p>
 * The interface is used by either a {@code Client} or a {@code Server}. There
 * is no need to create your own packet sender.
 * @author Daniel
 * @see Client#sendPacket(Packet) 
 * @see Server#sendPacket(int, Packet)
 */
public interface PacketSender {
    /**
     * Indicates whether the sender has met requirements to send.
     * @return True if the sender is ready to send
     */
    public boolean isReady();
    
    /**
     * The queue must be handled by the implementation.
     * @return The queue of packets that are waiting to be sent
     */
    public Queue<Packet> getQueuedPackets();
}
