package fi.riissanen.gwent.engine.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Queue;

/**
 * An adapter to the socket and sends queued packets.
 * @see PacketSender
 * @author Daniel
 */
public class OutputPacketAdapter implements Runnable {

    private final PacketSender sender;
    private final Socket socket;
    
    /**
     * Initializes with a {@code Socket} and {@code PacketSender}.
     * @param socket The socket over which packets are sent
     * @param sender The sender who provides the packets
     */
    public OutputPacketAdapter(Socket socket, PacketSender sender) {
        this.socket = socket;
        this.sender = sender;
    }

    public synchronized Socket getSocket() {
        return socket;
    }
    
    @Override
    public void run() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            while (true) {
                if (sender.isReady()) {
                    Queue<Packet> queue = sender.getQueuedPackets();
                    synchronized (queue) {
                        while (!queue.isEmpty()) {
                            Packet packet = queue.poll();
                            out.writeObject(packet);
                        }
                    }
                }
                try {
                    Thread.sleep(10); // Let other processes use the CPU
                } catch (InterruptedException ex) {
                }
            }
        } catch (IOException ex) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
            }
        }
    }
}
