package fi.riissanen.gwent.engine.net;

import java.util.Queue;

/**
 * Interface used to send packets with
 * 
 * <p>
 * The interface is used by either a {@code Client} or a {@code Server}. There
 * is no need to create your own packet sender.
 * @author Daniel
 * @see Client#sendPacket(Packet) 
 * @see Server#sendPacket(int, Packet)
 */
public interface PacketSender {
    public boolean isReady();
    public Queue<Packet> getQueuedPackets();
}
