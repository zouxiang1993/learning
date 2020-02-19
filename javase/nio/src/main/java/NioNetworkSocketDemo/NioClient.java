package NioNetworkSocketDemo;

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
import java.util.concurrent.Executors;

public class NioClient {
    public static void main(String[] args) throws IOException {
        SocketChannel client = SocketChannel.open();
        client.configureBlocking(false);
        client.connect(new InetSocketAddress(9999));
        Selector selector = Selector.open();
        client.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ);
        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iter = selectionKeys.iterator();
            while (iter.hasNext()) {
                SelectionKey selectionKey = iter.next();
                if (selectionKey.isConnectable()) {
                    SocketChannel sc = (SocketChannel) selectionKey.channel();
                    if (sc.isConnectionPending()) {
                        sc.finishConnect();

                        Executors.newSingleThreadExecutor().submit(() -> {
                            while (true) {
                                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                                String line = br.readLine();
                                ByteBuffer bf = ByteBuffer.allocate(1024);
                                bf.put(line.getBytes());
                                bf.flip();
                                sc.write(bf);
                            }
                        });
                    }
                    iter.remove();
                } else if (selectionKey.isReadable()) {
                    SocketChannel sc = (SocketChannel) selectionKey.channel();
                    ByteBuffer bf = ByteBuffer.allocate(1024);
                    int count = sc.read(bf);
                    String message = new String(bf.array(), 0, count);
                    System.out.println(message);
                    iter.remove();
                }
            }
        }
    }
}
