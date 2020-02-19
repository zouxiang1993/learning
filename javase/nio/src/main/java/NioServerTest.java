import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServerTest {
    public static void main(String[] args) throws IOException {
        int[] ports = new int[]{5000, 5001, 5002, 5003, 5004};
        Selector selector = Selector.open();
        for (int port : ports) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口号: " + port);
        }

        while (true) {
            int numSelected = selector.select();
            if (numSelected == 0) {
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println(selectionKeys);
            Iterator<SelectionKey> iterator = selectionKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();

                if (selectionKey.isAcceptable()) {
                    System.out.println("Acceptable 事件");
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("获取客户端连接: " + socketChannel);
                } else if (selectionKey.isReadable()) {
                    System.out.println("Readable 事件");
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer bf = ByteBuffer.allocate(1000);
                    int readBytes = socketChannel.read(bf);
                    if (readBytes <= 0) {
                        break;
                    }
                    System.out.println("读到字节数: " + readBytes);
                    bf.flip();
                    while (bf.hasRemaining()){
                        System.out.println((char)bf.get());
                    }
                }
                iterator.remove();
            }
        }
    }
}
