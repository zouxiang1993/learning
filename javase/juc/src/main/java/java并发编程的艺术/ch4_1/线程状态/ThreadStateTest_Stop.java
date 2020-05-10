package java并发编程的艺术.ch4_1.线程状态;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadStateTest_Stop {
    public static void main(String[] args) throws Exception {
        Thread thread1 = new Thread(new BlockedThread(), "BlockedThread_1");
        thread1.start();

        Thread.sleep(1000); // 让第一个线程有充分的时间去获取锁。

        new Thread(new BlockedThread(), "BlockedThread_2").start();

        Thread.sleep(2000);

        thread1.stop();
        System.out.println(thread1.getState().name());

        Thread.sleep(Integer.MAX_VALUE);
    }

    static Lock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    static class BlockedThread implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                condition.await();
                while (true) {
                    System.out.println("当前持有锁的线程是: " + Thread.currentThread().getName());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
