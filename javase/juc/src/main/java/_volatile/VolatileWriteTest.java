package _volatile;

/**
 * volatile写性能测试
 */
public class VolatileWriteTest {
    /**
     * 普通变量写 1000000000 次 : 38 ms
     * volatile变量写 1000000000 次 : 6313 ms
     */
    private volatile int delta = 0;

    public void work() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for (int j = 0; j < 1000000000; j++) {
                    delta = j;
                }
                long end = System.currentTimeMillis();
                System.out.println("总耗时: " + (end - start));
            }
        }, "AAA").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                delta = -1;
            }
        }, "BBB").start();
    }

    public static void main(String[] args) {
        new VolatileWriteTest().work();
    }

}
