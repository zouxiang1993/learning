package tree;

import tree.printer.LevelOrderPrinter;

public class MainTest {
    public static void main(String[] args) {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        LevelOrderPrinter printer = new LevelOrderPrinter(bst);
        bst.add(5);
        System.out.println(new LevelOrderPrinter(bst).printString() + "\r\n");
        bst.add(4);
        System.out.println(new LevelOrderPrinter(bst).printString() + "\r\n");
        bst.add(3);
        System.out.println(new LevelOrderPrinter(bst).printString() + "\r\n");
        bst.add(1);
        System.out.println(new LevelOrderPrinter(bst).printString() + "\r\n");
        bst.add(0);
        System.out.println(new LevelOrderPrinter(bst).printString() + "\r\n");
        bst.add(2);
        System.out.println(new LevelOrderPrinter(bst).printString() + "\r\n");
        bst.add(9);
        System.out.println(new LevelOrderPrinter(bst).printString() + "\r\n");
        bst.add(8);
        System.out.println(new LevelOrderPrinter(bst).printString() + "\r\n");
        bst.add(7);
        System.out.println(new LevelOrderPrinter(bst).printString() + "\r\n");
        bst.add(6);
        System.out.println(new LevelOrderPrinter(bst).printString() + "\r\n");
    }
}
