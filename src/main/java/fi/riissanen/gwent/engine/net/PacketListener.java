package fi.riissanen.gwent.engine.net;

/**
 * Interface used to listen for packets
 * 
 * <p>
 * The interface must be added to either a {@code Client} or a {@code Server}.
 * 
 * @author Daniel
 * @see Client#setPacketListener(PacketListener) 
 * @see Server#setPacketListener(PacketListener)
 */
public interface PacketListener {
    public void receivedPacket(Packet packet);
}
