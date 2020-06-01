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
     * 取值在[0,1]之间。通常情况下：
     * 这个值越大时，构造的时间越长，构造出来的双数组越小。(优先考虑内存消耗)
     * 这个值越小时，构造的时间越短，构造出来的双数组越大。(优先考虑构造时间)
     */
    private static final double THRESHOLD = 0.95;

    /**
     * 唯一的构建方法
     *
     * @param patterns 所有的模式串，必须严格按字典序排列
     * @param values   每个key对应的值，留空使用key的下标作为值
     * @return 构造完毕的DAT
     */
    public DoubleArrayTrie build(List<String> patterns, int values[]) {
        Objects.requireNonNull(patterns);
        // TODO: 等于0时怎么构造空树？
        if (patterns.size() == 0) {
            return new DoubleArrayTrie(null, null);
        }

        this.patterns = patterns;
        this.values = values;

        // 给双数组一个初始的大小: 32个双字节
        ensureSize(65536 * 32);

        // 由于base[pos]=0表示未占用，所以最少要设置成1
        base[0] = 1; // TODO: 这个设置要与insert过程的结果对应？
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
        int prevCode = 0;

        for (int i = parent.left; i < parent.right; i++) {
            final String pattern = patterns.get(i);

            // 根据pattern.length 和 parent.depth 的比较结果，分三种情况讨论。
            int currentCode;
            if (pattern.length() < parent.depth) {
                // 1. pattern长度太短，不可能是parent的子节点了，直接忽略
                continue;
            } else if (pattern.length() == parent.depth) {
                // 2. 到达的模式串的结尾，添加一个code=0的叶子节点
                currentCode = 0;
            } else {
                // 3. 添加一个非叶子节点 (也可能是连续的多个pattern在这个位置上有相同的字符，则合并成一个节点)
                assert pattern.length() > parent.depth;
                // 由于code=0专门用于表示叶子节点(即模式串的结束)。因此其他所有的code都必须大于0，所以这里要加1
                currentCode = (int) pattern.charAt(parent.depth) + 1;
            }

            if (prevCode > currentCode) {
                throw new RuntimeException(String.format("模式串没有严格的按字典序！[%s] 和 [%s]",
                        patterns.get(i - 1), patterns.get(i)));
            }

            // children.size() == 0 表示这是第一次执行
            // currentCode != prevCode 表示发现了这一层中的一个新节点。
            // 这两种情况都要新增一个Node
            if (currentCode != prevCode || children.size() == 0) {
                Node newNode = new Node();
                newNode.depth = parent.depth + 1;
                newNode.code = currentCode;
                newNode.left = i;
                // 如果新增的不是第一个节点，那么上一个节点就已经构建完毕，设置一下它的right属性
                if (children.size() != 0) {
                    lastOf(children).right = i;
                }
                children.add(newNode);
            }

            prevCode = currentCode;
        }

        // 设置最右节点的right属性
        if (!children.isEmpty()) {
            lastOf(children).right = parent.right;
        }

        return children;
    }

    /**
     * 插入节点
     *
     * @param siblings 一批等待插入的兄弟节点
     * @return siblings的父节点的基础偏移量(base值)
     */
    private int insert(List<Node> siblings, BitSet used) {
        // TODO: 考虑删除掉used参数，用check[pos] ==0 来判断是否被占用？
        int parentNodeBaseOffset = findCheckPos(siblings, used);
        used.set(parentNodeBaseOffset);

        // TODO: size参数是否可以去掉？
        if (size <= parentNodeBaseOffset + lastOf(siblings).code + 1) {
            size = parentNodeBaseOffset + lastOf(siblings).code + 1;
        }

        // 更新check[]。注意: 这个循环和下面的循环不能合并成一个。
        // 因为后面递归处理子节点时要用check[]来判断节点是否已经被占用，所以要先更新，再递归处理。
        for (int i = 0; i < siblings.size(); i++) {
            check[parentNodeBaseOffset + siblings.get(i).code] = parentNodeBaseOffset;
        }

        for (int i = 0; i < siblings.size(); i++) {
            Node currentNode = siblings.get(i);
            List<Node> children = createAllChildren(currentNode);
            if (children.size() > 0) {
                // 递归处理所有的子节点
                int currentNodeBaseOffset = insert(children, used);
                // 非叶子节点的base值
                base[parentNodeBaseOffset + currentNode.code] = currentNodeBaseOffset;
            } else {
                // 叶子节点(一个词的末尾)的base值
                base[parentNodeBaseOffset + currentNode.code] = (values != null) ?
                        (-values[currentNode.left] - 1) : (-currentNode.left - 1);

                if (values != null && (-values[currentNode.left] - 1) >= 0) {
                    throw new RuntimeException("errorCode = -2 ???");
                }
            }
        }
        return parentNodeBaseOffset;
    }

    /**
     * 在check[]中找出一个空闲的位置，作为这一批节点的基础偏移量
     * 即找到一个值offset,使得 check[offset] == 0 ，
     * 且对于siblings中的每一个节点，都有 base[offset+siblings.get(i).code] == 0
     *
     * @param siblings
     * @param used
     * @return
     */
    private int findCheckPos(List<Node> siblings, BitSet used) {
        int pos = Math.max(firstOf(siblings).code + 1, nextCheckPos) - 1;
        int nonZeroNum = 0;

        boolean isFirst = true;
        int begin;
        // 此循环体的目标是找出满足base[begin + a1...an]  == 0的n个空闲空间,a1...an是siblings中的n个节点
        outer:
        while (true) {
            pos++;
            begin = pos - firstOf(siblings).code; // 当前位置离第一个兄弟节点的距离

            if (check.length <= begin + lastOf(siblings).code) {
                ensureSize(begin + lastOf(siblings).code + Character.MAX_VALUE);
            }

            if (check[pos] != 0) { // 该位置已经被占用
                nonZeroNum++;
                continue;
            } else if (isFirst) {
                nextCheckPos = pos;
                isFirst = false;
            }

            // begin位置已经被占用
            if (used.get(begin)) {
                continue;
            }

            //
            for (int i = 1; i < siblings.size(); i++) {
                // TODO: 这里是不是应该改成base数组？？？
                if (check[begin + siblings.get(i).code] != 0) {
                    continue outer;
                }
            }

            break;
        }

        // 一个简单的启发式判断条件：
        // 如果从nextCheckPos到pos之间的空间已经占用了95%以上，那么再下次插入节点时，直接从pos位置处开始查找。
        if (1.0 * nonZeroNum / (pos - nextCheckPos + 1) >= THRESHOLD) {
            nextCheckPos = pos;
        }

        return begin;
    }

    /**
     * 将双数组扩展到给定的大小
     *
     * @param newSize
     */
    private void ensureSize(int newSize) {
        if (base != null && base.length > newSize) {
            throw new RuntimeException("新数组长度 < 旧数组长度，请检查是否算术溢出");
        }

        // TODO: 新老数组长度差距不能太小，不然可能很快又要进行下一次扩容。

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

    /**
     * 返回List中的最后一个节点
     */
    private Node lastOf(List<Node> nodes) {
        return nodes.get(nodes.size() - 1);
    }

    /**
     * 返回List中的第一个节点
     */
    private Node firstOf(List<Node> nodes) {
        return nodes.get(0);
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
