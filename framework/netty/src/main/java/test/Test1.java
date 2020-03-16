package test;

import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.channel.unix.DomainSocketAddress;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * Author: zouxiang
 * Date: 2020/3/16
 * Description: No Description
 */
public class Test1 {
    public static void main(String[] args) {
        NioSocketChannel sc = new NioSocketChannel();
        ChannelFuture future = sc.connect(new DomainSocketAddress("www.baidu.com"));
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                System.out.println("哈哈");
            }
        });
    }
}
