package ch09_bridge;

public class Main {
    public static void main(String[] args) {
        Display d1 = new Display(new StringDisplayImpl("Hello, China."));
        Display d2 = new CountDisplay(new StringDisplayImpl("Helllo, World."));
        CountDisplay d3 = new CountDisplay(new StringDisplayImpl("Hello, Universe."));

        d1.display();
        System.out.println();

        d2.display();
        System.out.println();

        d3.display();
        System.out.println();

        d3.multiDisplay(5);
    }
}
