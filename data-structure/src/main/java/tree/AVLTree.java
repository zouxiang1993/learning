package tree;

import java.util.Comparator;

public class AVLTree<E> extends BinarySearchTree<E> {
    public AVLTree() {
        super();
    }

    public AVLTree(Comparator<E> comparator) {
        super(comparator);
    }

    @Override
    protected void afterAdd(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                // 更新高度
                updateHeight(node);
            } else {
                // 恢复平衡
                rebalance(node);
                break;
            }
        }
    }

    @Override
    protected void afterRemove(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                // 更新高度
                updateHeight(node);
            } else {
                // 恢复平衡
                rebalance(node);
            }
        }
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode<>(element, parent);
    }

    /**
     * 节点是否平衡
     *
     * @param node
     * @return
     */
    private boolean isBalanced(Node<E> node) {
        int balanceFactor = ((AVLNode<E>) node).balanceFactor();
        return balanceFactor <= 1 && balanceFactor >= -1;
    }

    private void updateHeight(Node<E> node) {
        AVLNode avlNode = (AVLNode) node;
        avlNode.updateHeight();
    }

    /**
     * 恢复平衡
     *
     * @param grand 高度最低的那个不平衡节点
     */
    private void rebalance(Node<E> grand) {
        AVLNode parent = ((AVLNode) grand).tallerChild();
        AVLNode node = ((AVLNode) parent).tallerChild();

        if (parent.isLeftChild()) {
            if (node.isLeftChild()) {
                // LL
                rotateRight(grand);
            } else {
                // LR
                rotateLeft(parent);
                rotateRight(grand);
            }
        } else {
            if (node.isLeftChild()) {
                // RL
                rotateRight(parent);
                rotateLeft(grand);
            } else {
                // RR
                rotateLeft(grand);
            }
        }
    }

    private void rotateLeft(Node<E> grand) {
        Node<E> parent = grand.right;
        Node<E> child = parent.left;
        grand.right = parent.left;
        parent.left = grand;

        afterRotate(grand, parent, child);
    }

    private void rotateRight(Node<E> grand) {
        Node<E> parent = grand.left;
        Node<E> child = parent.right;
        grand.left = parent.right;
        parent.right = grand;

        afterRotate(grand, parent, child);
    }

    private void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else {
            root = parent;
        }

        if (child != null) {
            child.parent = grand;
        }

        grand.parent = parent;

        updateHeight(grand);
        updateHeight(parent);
    }

    private static class AVLNode<E> extends Node<E> {
        int height = 1;

        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        /**
         * 平衡因子: 左子树高度-右子树高度
         *
         * @return
         */
        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode) right).height;
            return leftHeight - rightHeight;
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode) right).height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }

        public AVLNode<E> tallerChild() {
            int leftHeight = left == null ? 0 : ((AVLNode) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode) right).height;
            if (leftHeight > rightHeight) {
                return (AVLNode<E>) left;
            }
            if (leftHeight < rightHeight) {
                return (AVLNode<E>) right;
            }
            if (isLeftChild()) {
                return (AVLNode<E>) left;
            } else {
                return (AVLNode<E>) right;
            }
        }

//        @Override
//        public String toString() {
//            return (element == null ? "null" : element.toString())
//                    + "_"
//                    + (parent == null ? "null" : String.valueOf(parent.element))
//                    + "_"
//                    + height;
//        }
    }
}
