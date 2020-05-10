package java并发编程的艺术.ch4_1.线程状态;

import java.util.Scanner;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/9
 */
public class ThreadStateTest_Runnable_2 {
    public static void main(String[] args) throws Exception {
        new Thread(new BlockIOThread(), "BlockIOThread").start();

        Thread.sleep(Integer.MAX_VALUE);
    }

    static class BlockIOThread implements Runnable {

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();  // 阻塞在InputStream.read()方法上
        }
    }
}
