package NetworkIODemo.SingleThreadReactor.client;

/**
 * Author: zouxiang
 * Date: 2020/2/20
 * Description: No Description
 */
public class ClientBootstrap {
    public static void main(String[] args) {
        new Thread(new NIOClient("127.0.0.1", 2333)).start();
        new Thread(new NIOClient("127.0.0.1", 2333)).start();
    }
}
