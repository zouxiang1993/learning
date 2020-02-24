package NetworkIODemo.SingleThreadReactor.server;

import java.io.IOException;

/**
 * Author: zouxiang
 * Date: 2020/2/20
 * Description: No Description
 */
public class ServerBootstrap {
    public static void main(String[] args) throws IOException {
        new Thread(new Reactor(2333)).start();
    }
}
