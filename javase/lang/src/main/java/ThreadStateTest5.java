/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/9
 */
public class ThreadStateTest5 {
    public static void main(String[] args) throws Exception {
        new Thread(new WaitingThread(), "WaitingThread_1").start();

        synchronized (obj) {
            // main线程能够获取到锁，说明 WaitingThread 一定已经调用了wait()方法
            obj.notify();

            while (true) {
                // 唤醒 WaitingThread, 但是不释放锁。
            }
        }

    }

    static Object obj = new Object();

    /**
     * java.lang.Thread.State: BLOCKED (on object monitor)
     */
    static class WaitingThread implements Runnable {

        @Override
        public void run() {
            try {
                synchronized (obj) {
                    obj.wait();
                    // 从waiting状态中被notify()唤醒以后，线程进入BLOCKED状态，只有重新获取到锁之后才能进入RUNNABLE状态。
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
