import java.util.Scanner;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/9
 */
public class ThreadStateTest1 {
    public static void main(String[] args) throws Exception {
        new Thread(new RunningThread(), "RunningThread").start();
        new Thread(new BlockIOThread(), "BlockIOThread").start();

        Thread.sleep(Integer.MAX_VALUE);
    }

    /**
     * 正在CPU上执行的线程 —— java.lang.Thread.State: RUNNABLE
     *
     */
    static class RunningThread implements Runnable {

        @Override
        public void run() {
            int tot = 0;
            while (true) {
                tot = tot + 1;
            }
        }
    }

    // TODO: 开100个RunningThread, 看所有线程的状态。

    /**
     * 阻塞在I/O操作上的线程 —— java.lang.Thread.State: RUNNABLE
     */
    static class BlockIOThread implements Runnable {

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in); // 阻塞在InputStream.read()方法上
            String line = scanner.nextLine();
            System.out.println(line);
            while (true) {

            }
        }
    }
}
