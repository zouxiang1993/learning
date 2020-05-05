package _volatile;

/**
 * volatile读性能测试
 */
public class VolatileReadTest {

    /**
     * 普通变量读 1000000000 次 : 56 ms
     * volatile变量读 1000000000 次 : 1779 ms
     */
    private int total;
    private /*volatile*/ int delta = 0;

    public void work() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for (int j = 0; j < 1000000000; j++) {
                    total = total + delta;
                }
                long end = System.currentTimeMillis();
                System.out.println("总耗时: " + (end - start));
                System.out.println("最终结果: " + total);
            }
        }, "AAA").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                delta = 1;
            }
        }, "BBB").start();
    }

    public static void main(String[] args) {
        new VolatileReadTest().work();
    }
}
