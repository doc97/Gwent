package fi.riissanen.gwent.game.net;

import fi.riissanen.gwent.engine.net.Packet;
import fi.riissanen.gwent.engine.net.PacketListener;
import fi.riissanen.gwent.engine.net.Server;
import java.io.IOException;

/**
 *
 * @author Daniel
 */
public class ServerTest implements PacketListener {

    private final Server server;
    private final int port;
    
    public ServerTest(int port) {
        this.port = port;
        server = new Server();
        server.setPacketListener(this);
    }
    
    public void launch() {
        try {
            System.out.println("Starting server at port " + port);
            server.listen(port);
        } catch (IOException ex) {
            System.err.println("Failed to create server socket");
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                System.err.println("Failed to close server socket");
            }
        }
    }
    
    @Override
    public void receivedPacket(Packet packet) {
        if (packet instanceof StringPacket) {
            String message = ((StringPacket) packet).getMessage();
            System.out.println("Client [" + packet.getSenderID() + "]: " + message);
            server.sendPacket(packet.getSenderID(), new StringPacket(server.getID(), message));
            System.out.println("Server: " + message);
        }
    }
    
    public static void main(String[] args) {
        new ServerTest(6666).launch();
    }
}
