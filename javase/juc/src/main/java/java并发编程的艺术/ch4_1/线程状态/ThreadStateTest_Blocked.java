package java并发编程的艺术.ch4_1.线程状态;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/9
 */
public class ThreadStateTest_Blocked {
    public static void main(String[] args) throws Exception {
        new Thread(new BlockedThread(), "BlockedThread_1").start();

        Thread.sleep(2000); // 睡两秒，让第一个线程有充分的时间去获取锁。

        new Thread(new BlockedThread(), "BlockedThread_2").start();

        Thread.sleep(Integer.MAX_VALUE);
    }

    static Object obj = new Object();

    static class BlockedThread implements Runnable {
        @Override
        public void run() {
            synchronized (obj) {
                // 获取锁后死循环, 不释放
                while (true) {
                }
            }
        }
    }

}
