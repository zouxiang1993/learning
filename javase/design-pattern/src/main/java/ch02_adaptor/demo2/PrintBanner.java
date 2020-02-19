package ch02_adaptor.demo2;

public class PrintBanner extends Print {
    private Banner delegate;

    public PrintBanner(String string) {
        this.delegate = new Banner(string);
    }

    @Override
    public void printWeak() {
        delegate.showWithParen();
    }

    @Override
    public void printStrong() {
        delegate.showWithAster();
    }
}
