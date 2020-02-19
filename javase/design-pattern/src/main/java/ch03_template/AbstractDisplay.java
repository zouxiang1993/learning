package ch03_template;

public abstract class AbstractDisplay {
    public abstract void open();

    public abstract void print();

    public abstract void close();

    public final void display() { // 模板方法
        open();
        for (int i = 0; i < 5; i++) {
            print();
        }
        close();
    }
}
