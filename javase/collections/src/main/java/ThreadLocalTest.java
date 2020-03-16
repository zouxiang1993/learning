/**
 * Author: zouxiang
 * Date: 2020/2/25
 * Description: No Description
 */
public class ThreadLocalTest {
    public static void main(String[] args) {
        ThreadLocal<Integer> integerThreadLocal = ThreadLocal.withInitial(() -> 111);

        ThreadLocal<String> stringThreadLocal = ThreadLocal.withInitial(() -> "sss");

        Thread thread = Thread.currentThread();
        System.out.println(thread);
    }
}
