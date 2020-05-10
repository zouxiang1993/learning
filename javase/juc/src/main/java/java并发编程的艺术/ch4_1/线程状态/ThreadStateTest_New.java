package java并发编程的艺术.ch4_1.线程状态;

public class ThreadStateTest_New {
    public static void main(String[] args) {
        Thread thread = new Thread();
        // NEW
        System.out.println(thread.getState().name());
    }
}
