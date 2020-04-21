package OP_READ作用;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 用来理解NIO中的  SelectionKey.OP_ACCEPT 和 OP_READ
 *
 * TODO: https://blog.csdn.net/spectrumleeee/article/details/39665799
 * 验证OP_WRITE的作用
 */
public class NIOServer {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress(9527), 5);
        System.out.println("bind & listen ..");

        Selector serverSelector = Selector.open();
        server.register(serverSelector, SelectionKey.OP_ACCEPT);
        SocketChannel client;
        while (true) {
            int select = serverSelector.selectNow();
            client = server.accept();
            if (select == 0) {
                System.out.println("暂时没有收到客户端连接... " + select + "\t" + client);
                Thread.sleep(1000);
            } else {
                System.out.println("收到客户端连接: " + select + "\t" + client);
                break;
            }
        }

        Selector clientSelector = Selector.open();
        client.configureBlocking(false);
        SelectionKey clientReadRegisterKey = client.register(clientSelector, SelectionKey.OP_READ);
        ByteBuffer buffer = ByteBuffer.allocate(10);
        while (true) {
            int select = clientSelector.selectNow();
            int readBytes = client.read(buffer);
            if (select == 0) {
                System.out.println("读未就绪...  " + select + "\t" + readBytes);
                Thread.sleep(1000);
            } else {
                System.out.println("读已经就绪了!!!  " + select + "\t" + readBytes);
                break;
            }
        }
        System.out.println("读到的数据是: " + new String(buffer.array()));
    }
}
