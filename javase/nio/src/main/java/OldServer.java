import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class OldServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(9999));
        while (true) {
            final Socket socket = serverSocket.accept();
            new Runnable() {
                @Override
                public void run() {
                    try {
                        socket.getInputStream();
                    } catch (Exception e) {

                    }
                }
            }.run();
        }
    }
}
