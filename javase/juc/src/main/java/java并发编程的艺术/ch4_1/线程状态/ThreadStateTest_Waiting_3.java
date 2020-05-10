package java并发编程的艺术.ch4_1.线程状态;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadStateTest_Waiting_3 {
    public static void main(String[] args) throws Exception {
        new Thread(new WaitingThread(), "WaitingThread_1").start();

        Thread.sleep(2000); // 睡两秒，让第一个线程有充分的时间去获取锁。

        new Thread(new WaitingThread(), "WaitingThread_2").start();

        Thread.sleep(Integer.MAX_VALUE);
    }

    static Lock lock = new ReentrantLock();

    static class WaitingThread implements Runnable {

        @Override
        public void run() {
            lock.lock(); // 尝试获取锁。
        }
    }
}
