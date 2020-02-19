import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioServerTest_client {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress(8899));

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectionKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey selectionKey = iter.next();
                if (selectionKey.isConnectable()) {
                    SocketChannel client = (SocketChannel) selectionKey.channel();

                    if (client.isConnectionPending()) {
                        client.finishConnect();
                        client.register(selector, SelectionKey.OP_READ);

//                        ByteBuffer bf = ByteBuffer.allocate(1024);
//                        bf.put(new Date().toString().getBytes());
//                        bf.flip();
//                        client.write(bf);

                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.submit(() -> {
                            try {
                                while (true) {
                                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                    String line = br.readLine();
                                    System.out.println("键盘输入: " + line);
                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    writeBuffer.put(line.getBytes());
                                    writeBuffer.flip();
                                    System.out.println(writeBuffer.limit());
                                    client.write(writeBuffer);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } else if (selectionKey.isReadable()) {
                    SocketChannel client = (SocketChannel) selectionKey.channel();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    int numBytes = client.read(readBuffer);
                    if (numBytes > 0) {
                        String message = new String(readBuffer.array(), 0, numBytes);
                        System.out.println(message);
                    }
                }
                iter.remove();
            }
        }

    }
}
