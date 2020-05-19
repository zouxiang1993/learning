package tree;

import java.util.Comparator;

public class BinarySearchTree<E> extends BinaryTree<E> {
    private Comparator<E> comparator; // 比较器

    public BinarySearchTree() {

    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    /**
     * 添加一个元素
     *
     * @param element
     */
    public void add(E element) {
        elementNotNullCheck(element);
        if (root == null) {
            root = new Node<E>(element, null);
            size++;
        }

        Node<E> current = root;
        Node<E> parent = null;
        int cmp = 0;
        while (current != null) {
            parent = current;
            cmp = compare(element, current.element);
            if (cmp > 0) {
                current = current.right;
            } else if (cmp < 0) {
                current = current.left;
            } else { // 相等
                current.element = element;
                return;
            }
        }

        Node<E> newNode = new Node(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
    }

    /**
     * 删除一个元素
     *
     * @param element
     */
    public void remove(E element) {
        remove(node(element));
    }

    boolean contains(E element) {
        return node(element) != null;
    }

    private void remove(Node<E> node) {
        if (node == null) {
            return;
        }
        size--;

        // 删除度为2的节点
        // 首先要理解一个事实: 度为2的节点的后继(前驱)节点的度必定是1或者0
        // 反证法： 如果节点A的度为2，且A的后继节点B的度也为2，那么B左子树存在，
        // 那么A的后继节点必须在B的左子树中，这与B是A的后继节点矛盾，原式得证。
        if (node.hasTwoChildren()) {
            // 找到后继节点 (也可以用前驱节点)
            Node<E> succ = successor(node);
            // 用后继节点的值替代当前节点的值
            node.element = succ.element;
            // 删除后继节点
            node = succ;
        }

        // 删除node节点 (node的度必然是1或者0)
        Node<E> replacement = node.left != null ? node.left : node.right;
        if (replacement != null) {
            // node的度为1
            replacement.parent = node.parent;

            if (node.parent == null) {
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else {
                node.parent.right = replacement;
            }
        } else if (node.parent == null) {
            root = null;
        } else {
            if (node == node.parent.right) {
                node.parent.right = null;
            } else {
                node.parent.left = null;
            }
        }
    }

    private Node<E> node(E element) {
        Node<E> node = root;
        while (node != null) {
            int cmp = compare(element, node.element);
            if (cmp == 0) {
                return node;
            }
            if (cmp > 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    /**
     * 前驱节点
     *
     * @param node
     * @return
     */
    private Node<E> predecessor(Node<E> node) {
        if (node == null) {
            return null;
        }

        // 如果左子树存在，则前驱节点为: (node.left.right.right.right......)
        Node<E> p = node.left;
        if (p != null) {
            while (p.right == null) {
                p = p.right;
            }
            return p;
        }

        // 如果左子树不存在，则前驱节点为:
        while (node.parent != null && node == node.parent.left) {
            node = node.parent;
        }
        return node.parent;
    }

    /**
     * 后继节点
     *
     * @param node
     * @return
     */
    private Node<E> successor(Node<E> node) {
        if (node == null) {
            return null;
        }

        Node<E> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }

    /**
     * 比较两个元素
     *
     * @param e1
     * @param e2
     * @return 如果相等，则返回0；如果e1 > e2，则返回正数；如果e1 < e2， 则返回负数
     */
    private int compare(E e1, E e2) {
        if (comparator != null) {
            return comparator.compare(e1, e2);
        }
        if (e1 instanceof Comparable) {
            Comparable c1 = (Comparable) e1;
            return c1.compareTo(e2);
        } else {
            throw new RuntimeException("元素未实现Comparable接口时，必须给定比较器comparator");
        }
    }
}
