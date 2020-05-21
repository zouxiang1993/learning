package tree;

import tree.printer.BinaryTreeInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 二叉树
 *
 * @param <E> 节点元素类型
 */
public class BinaryTree<E> implements BinaryTreeInfo {
    protected int size; // 节点总数
    protected Node<E> root; // 根节点

    public int size() {
        return size;
    }

    /**
     * @return 是否是空树
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 清空整棵树
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * 从根节点开始进行中序遍历
     */
    public List<E> inorderTraversal() {
        return inorderTraversal(root);
    }

    /**
     * 从给定的节点开始进行中序遍历
     */
    private List<E> inorderTraversal(Node<E> node) {
        if (node == null) {
            return Collections.emptyList();
        }
        List<E> leftList = inorderTraversal(node.left);
        List<E> rightList = inorderTraversal(node.right);
        ArrayList<E> result = new ArrayList<>(leftList.size() + rightList.size() + 1);
        result.addAll(leftList);
        result.add(node.element);
        result.addAll(rightList);
        return result;
    }

    protected void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
    }

    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element, parent);
    }

    protected static class Node<E> {
        E element; // 节点中存储的元素
        Node<E> left; // 左子节点
        Node<E> right;  // 右子节点
        Node<E> parent;  // 父节点

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        /**
         * @return 是否是叶子节点
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * @return 是否有两个子节点
         */
        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        @Override
        public String toString() {
            return String.valueOf(element);
        }
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return node == null ? null : ((Node) node).left;
    }

    @Override
    public Object right(Object node) {
        return node == null ? null : ((Node) node).right;
    }

    @Override
    public Object string(Object node) {
        return node == null ? "NULL" : node.toString();
    }
}
