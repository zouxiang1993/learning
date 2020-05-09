/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/9
 */
public class ThreadStateTest4 {
    public static void main(String[] args) throws Exception {
        new Thread(new WaitingThread(), "WaitingThread_1").start();

        Thread.sleep(Integer.MAX_VALUE);
    }

    static Object obj = new Object();

    /**
     * java.lang.Thread.State: WAITING (on object monitor)
     */
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
