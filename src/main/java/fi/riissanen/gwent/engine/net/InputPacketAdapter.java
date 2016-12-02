package fi.riissanen.gwent.engine.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * An adapter to the socket input stream that listens for incoming packets.
 * @See PacketListener
 * @author Daniel
 */
public class InputPacketAdapter implements Runnable {

    private final PacketListener listener;
    private final Socket socket;

    /**
     * Initializes with a {@code Socket} and {@code PacketListener}.
     * @param socket The socket
     * @param listener The listener
     */
    public InputPacketAdapter(Socket socket, PacketListener listener) {
        this.socket = socket;
        this.listener = listener;
    }

    public synchronized Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(socket.getInputStream());
            while (true) {
                Packet packet = (Packet) in.readObject();
                if (packet != null) {
                    listener.receivedPacket(packet);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
        }
    }
}
