package trie.dat;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;

/**
 * Description: Double Array Trie 的构建工具
 * 参考:
 * https://www.hankcs.com/program/java/%E5%8F%8C%E6%95%B0%E7%BB%84trie%E6%A0%91doublearraytriejava%E5%AE%9E%E7%8E%B0.html
 * https://github.com/hankcs/HanLP/blob/1.x/src/main/java/com/hankcs/hanlp/collection/trie/DoubleArrayTrie.java
 *
 * @author zouxiang
 * @date 2020/5/28
 */
public class Builder {
    /**
     * 所有的模式串，必须严格按字典序排序
     */
    private List<String> patterns;
    private int values[];

    private int base[];
    private int check[];
    /**
     * base 和 check 的大小  TODO: 记录的应该是被使用的最大的位置，用于最后缩容。 可以把这个变量删掉？
     */
    private int size;

    /**
     * 下一次插入时从check[]中的哪个位置开始找。
     */
    private int nextCheckPos;

    /**
     * 唯一的构建方法
     *
     * @param patterns 所有的模式串，必须严格按字典序排列
     * @param values   每个key对应的值，留空使用key的下标作为值
     * @return 构造完毕的DAT
     */
    public DoubleArrayTrie build(List<String> patterns, int values[]) {
        Objects.requireNonNull(patterns);
        if (patterns.size() == 0) {
            return new DoubleArrayTrie(null, null);
        }

        this.patterns = patterns;
        this.values = values;

        // 给双数组一个初始的大小: 32个双字节
        ensureSize(65536 * 32);

        base[0] = 1;
        nextCheckPos = 0;

        Node root = new Node();
        root.left = 0;
        root.right = patterns.size();
        root.depth = 0;

        List<Node> children = createAllChildren(root);
        insert(children, new BitSet());

        shrink();

        return new DoubleArrayTrie(base, check);
    }

    /**
     * 生成parent节点所有的直接子节点
     *
     * @param parent 父节点
     * @return 返回parent节点所有的子节点
     */
    private List<Node> createAllChildren(Node parent) {
        List<Node> children = new ArrayList<>();
        int prev = 0;

        for (int i = parent.left; i < parent.right; i++) {
            final String pattern = patterns.get(i);

            if (pattern.length() < parent.depth) {
                // 长度太短，不可能是parent的子节点了，直接忽略
                continue;
            }

            int cur = 0;
            if (pattern.length() != parent.depth) {
                // Q: 为什么要加1？
                // A: 在树中，code=0专门用来表示一个字符串的结束。因此其他的code都必须大于等于1
                cur = (int) pattern.charAt(parent.depth) + 1;
            }

            if (prev > cur) {
                throw new RuntimeException(String.format("模式串没有严格的按字典序！[%s] 和 [%s]",
                        patterns.get(i - 1), patterns.get(i)));
            }

            // children.size() == 0 表示这是第一次执行
            // cur != prev 表示发现了这一层中的一个新节点。
            // 这两种情况都要新增一个Node
            if (cur != prev || children.size() == 0) {
                Node newNode = new Node();
                newNode.depth = parent.depth + 1;
                newNode.code = cur;
                newNode.left = i;
                // 如果新增的不是第一个节点，那么上一个节点就已经构建完毕，设置一下它的right属性
                if (children.size() != 0) {
                    lastOf(children).right = i;
                }
                children.add(newNode);
            }

            prev = cur;
        }

        // 设置最右节点的right属性
        if (children.size() != 0) {
            lastOf(children).right = parent.right;
        }

        return children;
    }

    /**
     * 插入节点
     *
     * @param siblings 一批等待插入的兄弟节点
     * @return 插入位置
     */
    private int insert(List<Node> siblings, BitSet used) {
        int begin = findCheckPos(siblings, used);
        used.set(begin);

        // TODO: ...
        if (size <= begin + lastOf(siblings).code + 1) {
            size = begin + lastOf(siblings).code + 1;
        }

        for (int i = 0; i < siblings.size(); i++) {
            check[begin + siblings.get(i).code] = begin;
        }

        for (int i = 0; i < siblings.size(); i++) {
            List<Node> newChildren = createAllChildren(siblings.get(i));
            if (newChildren.size() > 0) {
                // 递归处理所有的子节点
                int h = insert(newChildren, used);
                base[begin + siblings.get(i).code] = h;
            } else {
                // 如果没有子节点，则说明是一个词的末尾且不为其他词的前缀
                base[begin + siblings.get(i).code] = (values != null) ?
                        (-values[siblings.get(i).left] - 1) : (-siblings.get(i).left - 1);

                if (values != null && (-values[siblings.get(i).left] - 1) >= 0) {
                    throw new RuntimeException("errorCode = -2 ???");
                }
            }
        }
        return begin;
    }

    /**
     * 返回List中的最后一个节点
     */
    private Node lastOf(List<Node> nodes) {
        return nodes.get(nodes.size() - 1);
    }

    private int findCheckPos(List<Node> siblings, BitSet used) {
        boolean isFirst = true;
        int pos = Math.max(siblings.get(0).code + 1, nextCheckPos) - 1;
        int nonZeroNum = 0;

        // 一个简单的启发式判断条件：
        // 如果从nextCheckPos到pos之间的空间已经占用了95%以上，那么再下次插入节点时，直接从pos位置处开始查找。
        if (1.0 * nonZeroNum / (pos - nextCheckPos + 1) >= 0.95) {
            nextCheckPos = pos;
        }

        int begin;
        // 此循环体的目标是找出满足base[begin + a1...an]  == 0的n个空闲空间,a1...an是siblings中的n个节点
        outer:
        while (true) {
            pos++;

            if (check.length <= pos) {
                ensureSize(pos + 1);
            }

            if (check[pos] != 0) {
                nonZeroNum++;
                continue;
            } else if (isFirst) {
                nextCheckPos = pos;
                isFirst = false;
            }

            begin = pos - siblings.get(0).code; // 当前位置离第一个兄弟节点的距离
            if (check.length <= begin + siblings.get(siblings.size() - 1).code) {
                ensureSize(begin + siblings.get(siblings.size() - 1).code + Character.MAX_VALUE);
            }

            if (used.get(begin)) {
                continue;
            }

            for (int i = 1; i < siblings.size(); i++) {
                if (check[begin + siblings.get(i).code] != 0) {
                    continue outer;
                }
            }

            break;
        }
        return begin;
    }

    /**
     * 将双数组扩展到给定的大小
     *
     * @param newSize
     */
    private void ensureSize(int newSize) {
        // TODO: 是否可能会存在频繁扩容的情况？是否要限制一下每次扩容的最小值。
        if (base != null && base.length >= newSize) {
            return;
        }

        int[] newBase = new int[newSize];
        int[] newCheck = new int[newSize];
        if (base != null && base.length > 0) {
            System.arraycopy(base, 0, newBase, 0, base.length);
            System.arraycopy(check, 0, newCheck, 0, check.length);
        }

        base = newBase;
        check = newCheck;
    }

    /**
     * 释放掉base[]和check[]占用的多余的内存
     */
    private void shrink() {
        int newBase[] = new int[size + 65535];
        System.arraycopy(base, 0, newBase, 0, size);
        base = newBase;

        int newCheck[] = new int[size + 65535];
        System.arraycopy(check, 0, newCheck, 0, size);
        check = newCheck;
    }

    private static class Node {
        /**
         * 节点上的字符
         */
        int code;
        /**
         * 节点的深度。根节点为0，根节点的子节点为1 ...
         */
        int depth;
        /**
         * 左子节点
         */
        int left;
        /**
         * 右子节点
         */
        int right;

        @Override
        public String toString() {
            return "Node{" +
                    "code=" + code +
                    ", depth=" + depth +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }
}
