package NetworkIODemo.bioServer;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Author: zouxiang
 * Date: 2020/2/20
 * Description: No Description
 */
public class BIOSingleThreadServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        int recvMsgSize = 0;
        InputStream in = null;
        try {
            serverSocket = new ServerSocket(2333); // 开一个监听2333端口的套接字
            byte[] recvBuf = new byte[1024];
            while (true) {
                Socket clientSocket = serverSocket.accept(); //阻塞，直到有新的客户端连接进来。
                SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
                System.out.println("连接成功，处理客户端: " + clientAddress);
                in = clientSocket.getInputStream();
                while ((recvMsgSize = in.read(recvBuf)) != -1) {
                    byte[] temp = new byte[recvMsgSize];
                    System.arraycopy(recvBuf, 0, temp, 0, recvMsgSize);
                    System.out.println("收到客户端" + clientAddress + "的消息，内容： " + new String(temp));
                }
                System.out.println("---------------------------------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    System.out.println("server socket 关闭");
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
