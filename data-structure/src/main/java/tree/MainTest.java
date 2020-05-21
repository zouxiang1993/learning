package tree;

import tree.printer.LevelOrderPrinter;

public class MainTest {
    public static void main(String[] args) {
        int[] ints = {85, 19, 69, 3, 7, 99, 95, 2, 1, 70, 44, 58, 11, 21, 14, 93, 57, 4, 56};
        BinarySearchTree<Integer> bst = new AVLTree<>();
        for (int i : ints) {
            bst.add(i);
        }
        bst.remove(21);
        LevelOrderPrinter printer = new LevelOrderPrinter(bst);
        System.out.println(new LevelOrderPrinter(bst).printString() + "\r\n");
    }
}
