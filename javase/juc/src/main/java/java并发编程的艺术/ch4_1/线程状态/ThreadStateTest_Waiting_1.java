package java并发编程的艺术.ch4_1.线程状态;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/9
 */
public class ThreadStateTest_Waiting_1 {
    public static void main(String[] args) throws Exception {
        new Thread(new WaitingThread(), "WaitingThread_1").start();

        Thread.sleep(Integer.MAX_VALUE);
    }

    static Object obj = new Object();

    static class WaitingThread implements Runnable {

        @Override
        public void run() {
            try {
                synchronized (obj) {
                    obj.wait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
