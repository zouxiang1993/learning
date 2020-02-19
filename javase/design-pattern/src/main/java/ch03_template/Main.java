package ch03_template;

public class Main {
    public static void main(String[] args) {
        AbstractDisplay d1 = new CharDisplay('H');
        AbstractDisplay d2 = new StringDisplay("Hello, world.");

        AbstractDisplay d3 = new StringDisplay("你好，世界。");

        d1.display();
        System.out.println();

        d2.display();
        System.out.println();

        d3.display();
    }
}
