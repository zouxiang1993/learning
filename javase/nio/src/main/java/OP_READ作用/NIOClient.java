package OP_READ作用;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NIOClient {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("open...");
        SocketChannel channel = SocketChannel.open();

        scanner.nextLine();
        System.out.println("bind...");
        channel.bind(new InetSocketAddress(9999));

        scanner.nextLine();
        System.out.println("connect...");
        channel.connect(new InetSocketAddress(9527));

        scanner.nextLine();
        System.out.println("开始写数据...");
        channel.write(ByteBuffer.wrap("abcdefg".getBytes()));
        System.out.println("写数据完毕");

        scanner.nextLine();

        ByteBuffer readBuffer = ByteBuffer.allocate(1024*1024);

        while (true) {
            int tot = 0;
            int readBytes = -1;
            while (readBytes != 0){
                readBytes = channel.read(readBuffer);
                tot += readBytes;
            }
            System.out.println("读到的字节数: " + tot);
            scanner.nextLine();
        }
    }
}
