package java并发编程的艺术.ch4_1.线程状态;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/9
 */
public class ThreadStateTest_Runnable_1 {
    public static void main(String[] args) throws Exception {
        // 当前电脑4核，但是测试结果显示5个线程都处于Runnable状态。
        for (int i = 0; i < Runtime.getRuntime().availableProcessors() + 1; i++) {
            new Thread(new RunningOrRunnableThread(), "RunningOrRunnableThread_" + i).start();
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 正在CPU上执行的线程 —— java.lang.Thread.State: RUNNABLE
     */
    static class RunningOrRunnableThread implements Runnable {

        @Override
        public void run() {
            int tot = 0;
            while (true) {
                tot = tot + 1;
            }
        }
    }
}
