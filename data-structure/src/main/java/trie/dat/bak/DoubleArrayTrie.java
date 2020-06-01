package trie.dat.bak;

import java.util.*;

public class DoubleArrayTrie<V> {

    protected int check[];
    protected int base[];
    protected V[] v;
    /**
     * base 和 check 的大小
     */
    protected int size;

    public int getSize() {
        return size;
    }

    /**
     * 精确匹配
     *
     * @param key 键
     * @return 值
     */
    public int exactMatchSearch(String key) {
        return exactMatchSearch(key, 0, 0, 0);
    }

    public int exactMatchSearch(String key, int pos, int len, int nodePos) {
        if (len <= 0)
            len = key.length();
        if (nodePos <= 0)
            nodePos = 0;

        int result = -1;

        int b = base[nodePos];
        int p;

        for (int i = pos; i < len; i++) {
            p = b + (int) (key.charAt(i)) + 1;
            if (b == check[p])
                b = base[p];
            else
                return result;
        }

        p = b;
        int n = base[p];
        if (b == check[p] && n < 0) {
            result = -n - 1;
        }
        return result;
    }

    /**
     * 精确查询
     *
     * @param keyChars 键的char数组
     * @param pos      char数组的起始位置
     * @param len      键的长度
     * @param nodePos  开始查找的位置（本参数允许从非根节点查询）
     * @return 查到的节点代表的value ID，负数表示不存在
     */
    public int exactMatchSearch(char[] keyChars, int pos, int len, int nodePos) {
        int result = -1;

        int b = base[nodePos];
        int p;

        for (int i = pos; i < len; i++) {
            p = b + (int) (keyChars[i]) + 1;
            if (b == check[p])
                b = base[p];
            else
                return result;
        }

        p = b;
        int n = base[p];
        if (b == check[p] && n < 0) {
            result = -n - 1;
        }
        return result;
    }

    public int myMatchTest(char[] text) {
        int result = -1;

        int baseOffset = base[0];
        int nextPos;

        for (int i = 0; i < text.length; i++) {
            nextPos = baseOffset + (int) (text[i]) + 1;
            if (baseOffset == check[nextPos])
                baseOffset = base[nextPos];
            else
                return result;
        }

        nextPos = baseOffset;
        int n = base[nextPos];
        if (baseOffset == check[nextPos] && n < 0) {
            result = -n - 1;
        }
        return result;
    }

    public List<Integer> commonPrefixSearch(String key) {
        return commonPrefixSearch(key, 0, 0, 0);
    }

    /**
     * 前缀查询
     *
     * @param key     查询字串
     * @param pos     字串的开始位置
     * @param len     字串长度
     * @param nodePos base中的开始位置
     * @return 一个含有所有下标的list
     */
    public List<Integer> commonPrefixSearch(String key, int pos, int len, int nodePos) {
        if (len <= 0)
            len = key.length();
        if (nodePos <= 0)
            nodePos = 0;

        List<Integer> result = new ArrayList<Integer>();

        char[] keyChars = key.toCharArray();

        int b = base[nodePos];
        int n;
        int p;

        for (int i = pos; i < len; i++) {
            p = b + (int) (keyChars[i]) + 1;    // 状态转移 p = base[char[i-1]] + char[i] + 1
            if (b == check[p])                  // base[char[i-1]] == check[base[char[i-1]] + char[i] + 1]
                b = base[p];
            else
                return result;
            p = b;
            n = base[p];
            if (b == check[p] && n < 0)         // base[p] == check[p] && base[p] < 0 查到一个词
            {
                result.add(-n - 1);
            }
        }

        return result;
    }

    /**
     * 前缀查询，包含值
     *
     * @param key 键
     * @return 键值对列表
     * @deprecated 最好用优化版的
     */
    public LinkedList<Map.Entry<String, V>> commonPrefixSearchWithValue(String key) {
        int len = key.length();
        LinkedList<Map.Entry<String, V>> result = new LinkedList<Map.Entry<String, V>>();
        char[] keyChars = key.toCharArray();
        int b = base[0];
        int n;
        int p;

        for (int i = 0; i < len; ++i) {
            p = b;
            n = base[p];
            if (b == check[p] && n < 0)         // base[p] == check[p] && base[p] < 0 查到一个词
            {
                result.add(new AbstractMap.SimpleEntry<String, V>(new String(keyChars, 0, i), v[-n - 1]));
            }

            p = b + (int) (keyChars[i]) + 1;    // 状态转移 p = base[char[i-1]] + char[i] + 1
            // 下面这句可能产生下标越界，不如改为if (p < size && b == check[p])，或者多分配一些内存
            if (b == check[p])                  // base[char[i-1]] == check[base[char[i-1]] + char[i] + 1]
                b = base[p];
            else
                return result;
        }

        p = b;
        n = base[p];

        if (b == check[p] && n < 0) {
            result.add(new AbstractMap.SimpleEntry<String, V>(key, v[-n - 1]));
        }

        return result;
    }

    /**
     * 优化的前缀查询，可以复用字符数组
     *
     * @param keyChars
     * @param begin
     * @return
     */
    public LinkedList<Map.Entry<String, V>> commonPrefixSearchWithValue(char[] keyChars, int begin) {
        int len = keyChars.length;
        LinkedList<Map.Entry<String, V>> result = new LinkedList<Map.Entry<String, V>>();
        int b = base[0];
        int n;
        int p;

        for (int i = begin; i < len; ++i) {
            p = b;
            n = base[p];
            if (b == check[p] && n < 0)         // base[p] == check[p] && base[p] < 0 查到一个词
            {
                result.add(new AbstractMap.SimpleEntry<String, V>(new String(keyChars, begin, i - begin), v[-n - 1]));
            }

            p = b + (int) (keyChars[i]) + 1;    // 状态转移 p = base[char[i-1]] + char[i] + 1
            // 下面这句可能产生下标越界，不如改为if (p < size && b == check[p])，或者多分配一些内存
            if (b == check[p])                  // base[char[i-1]] == check[base[char[i-1]] + char[i] + 1]
                b = base[p];
            else
                return result;
        }

        p = b;
        n = base[p];

        if (b == check[p] && n < 0) {
            result.add(new AbstractMap.SimpleEntry<String, V>(new String(keyChars, begin, len - begin), v[-n - 1]));
        }

        return result;
    }

    /**
     * 树叶子节点个数
     *
     * @return
     */
    public int size() {
        return v.length;
    }

    /**
     * 获取index对应的值
     *
     * @param index
     * @return
     */
    public V getValueAt(int index) {
        return v[index];
    }

    /**
     * 精确查询
     *
     * @param key 键
     * @return 值
     */
    public V get(String key) {
        int index = exactMatchSearch(key);
        if (index >= 0) {
            return getValueAt(index);
        }

        return null;
    }

    public V get(char[] key) {
        int index = exactMatchSearch(key, 0, key.length, 0);
        if (index >= 0) {
            return getValueAt(index);
        }

        return null;
    }

    public boolean containsKey(String key) {
        return exactMatchSearch(key) >= 0;
    }

    /**
     * 沿着路径转移状态
     *
     * @param path
     * @return
     */
    protected int transition(String path) {
        return transition(path.toCharArray());
    }

    /**
     * 沿着节点转移状态
     *
     * @param path
     * @return
     */
    protected int transition(char[] path) {
        int b = base[0];
        int p;

        for (int i = 0; i < path.length; ++i) {
            p = b + (int) (path[i]) + 1;
            if (b == check[p])
                b = base[p];
            else
                return -1;
        }

        p = b;
        return p;
    }

    /**
     * 沿着路径转移状态
     *
     * @param path 路径
     * @param from 起点（根起点为base[0]=1）
     * @return 转移后的状态（双数组下标）
     */
    public int transition(String path, int from) {
        int b = from;
        int p;

        for (int i = 0; i < path.length(); ++i) {
            p = b + (int) (path.charAt(i)) + 1;
            if (b == check[p])
                b = base[p];
            else
                return -1;
        }

        p = b;
        return p;
    }

    /**
     * 转移状态
     *
     * @param c
     * @param from
     * @return
     */
    public int transition(char c, int from) {
        int b = from;
        int p;

        p = b + (int) (c) + 1;
        if (b == check[p])
            b = base[p];
        else
            return -1;

        return b;
    }

    /**
     * 检查状态是否对应输出
     *
     * @param state 双数组下标
     * @return 对应的值，null表示不输出
     */
    public V output(int state) {
        if (state < 0) return null;
        int n = base[state];
        if (state == check[state] && n < 0) {
            return v[-n - 1];
        }
        return null;
    }

    /**
     * 一个搜索工具（注意，当调用next()返回false后不应该继续调用next()，除非reset状态）
     */
    public class Searcher {
        /**
         * key的起点
         */
        public int begin;
        /**
         * key的长度
         */
        public int length;
        /**
         * key的字典序坐标
         */
        public int index;
        /**
         * key对应的value
         */
        public V value;
        /**
         * 传入的字符数组
         */
        private char[] charArray;
        /**
         * 上一个node位置
         */
        private int last;
        /**
         * 上一个字符的下标
         */
        private int i;
        /**
         * charArray的长度，效率起见，开个变量
         */
        private int arrayLength;

        /**
         * 构造一个双数组搜索工具
         *
         * @param offset    搜索的起始位置
         * @param charArray 搜索的目标字符数组
         */
        public Searcher(int offset, char[] charArray) {
            this.charArray = charArray;
            i = offset;
            last = base[0];
            arrayLength = charArray.length;
            // A trick，如果文本长度为0的话，调用next()时，会带来越界的问题。
            // 所以我要在第一次调用next()的时候触发begin == arrayLength进而返回false。
            // 当然也可以改成begin >= arrayLength，不过我觉得操作符>=的效率低于==
            if (arrayLength == 0) begin = -1;
            else begin = offset;
        }

        /**
         * 取出下一个命中输出
         *
         * @return 是否命中，当返回false表示搜索结束，否则使用公开的成员读取命中的详细信息
         */
        public boolean next() {
            int b = last;
            int n;
            int p;

            for (; ; ++i) {
                if (i == arrayLength)               // 指针到头了，将起点往前挪一个，重新开始，状态归零
                {
                    ++begin;
                    if (begin == arrayLength) break;
                    i = begin;
                    b = base[0];
                }
                p = b + (int) (charArray[i]) + 1;   // 状态转移 p = base[char[i-1]] + char[i] + 1
                if (b == check[p])                  // base[char[i-1]] == check[base[char[i-1]] + char[i] + 1]
                    b = base[p];                    // 转移成功
                else {
                    i = begin;                      // 转移失败，也将起点往前挪一个，重新开始，状态归零
                    ++begin;
                    if (begin == arrayLength) break;
                    b = base[0];
                    continue;
                }
                p = b;
                n = base[p];
                if (b == check[p] && n < 0)         // base[p] == check[p] && base[p] < 0 查到一个词
                {
                    length = i - begin + 1;
                    index = -n - 1;
                    value = v[index];
                    last = b;
                    ++i;
                    return true;
                }
            }

            return false;
        }
    }

    public Searcher getSearcher(String text) {
        return getSearcher(text, 0);
    }

    public Searcher getSearcher(String text, int offset) {
        return new Searcher(offset, text.toCharArray());
    }

    public Searcher getSearcher(char[] text, int offset) {
        return new Searcher(offset, text);
    }

    /**
     * 一个最长搜索工具（注意，当调用next()返回false后不应该继续调用next()，除非reset状态）
     */
    public class LongestSearcher {
        /**
         * key的起点
         */
        public int begin;
        /**
         * key的长度
         */
        public int length;
        /**
         * key的字典序坐标
         */
        public int index;
        /**
         * key对应的value
         */
        public V value;
        /**
         * 传入的字符数组
         */
        private char[] charArray;
        /**
         * 上一个字符的下标
         */
        private int i;
        /**
         * charArray的长度，效率起见，开个变量
         */
        private int arrayLength;

        /**
         * 构造一个双数组搜索工具
         *
         * @param offset    搜索的起始位置
         * @param charArray 搜索的目标字符数组
         */
        public LongestSearcher(int offset, char[] charArray) {
            this.charArray = charArray;
            i = offset;
            arrayLength = charArray.length;
            begin = offset;
        }

        /**
         * 取出下一个命中输出
         *
         * @return 是否命中，当返回false表示搜索结束，否则使用公开的成员读取命中的详细信息
         */
        public boolean next() {
            value = null;
            begin = i;
            int b = base[0];
            int n;
            int p;

            for (; ; ++i) {
                if (i >= arrayLength)               // 指针到头了，将起点往前挪一个，重新开始，状态归零
                {
                    return value != null;
                }
                p = b + (int) (charArray[i]) + 1;   // 状态转移 p = base[char[i-1]] + char[i] + 1
                if (b == check[p])                  // base[char[i-1]] == check[base[char[i-1]] + char[i] + 1]
                    b = base[p];                    // 转移成功
                else {
                    if (begin == arrayLength) break;
                    if (value != null) {
                        i = begin + length;         // 输出最长词后，从该词语的下一个位置恢复扫描
                        return true;
                    }

                    i = begin;                      // 转移失败，也将起点往前挪一个，重新开始，状态归零
                    ++begin;
                    b = base[0];
                }
                p = b;
                n = base[p];
                if (b == check[p] && n < 0)         // base[p] == check[p] && base[p] < 0 查到一个词
                {
                    length = i - begin + 1;
                    index = -n - 1;
                    value = v[index];
                }
            }

            return false;
        }
    }

    public LongestSearcher getLongestSearcher(String text, int offset) {
        return getLongestSearcher(text.toCharArray(), offset);
    }

    public LongestSearcher getLongestSearcher(char[] text, int offset) {
        return new LongestSearcher(offset, text);
    }

    /**
     * 转移状态
     *
     * @param current
     * @param c
     * @return
     */
    protected int transition(int current, char c) {
        int b = base[current];
        int p;

        p = b + c + 1;
        if (b == check[p])
            b = base[p];
        else
            return -1;

        p = b;
        return p;
    }

    /**
     * 更新某个键对应的值
     *
     * @param key   键
     * @param value 值
     * @return 是否成功（失败的原因是没有这个键）
     */
    public boolean set(String key, V value) {
        int index = exactMatchSearch(key);
        if (index >= 0) {
            v[index] = value;
            return true;
        }

        return false;
    }

    /**
     * 从值数组中提取下标为index的值<br>
     * 注意为了效率，此处不进行参数校验
     *
     * @param index 下标
     * @return 值
     */
    public V get(int index) {
        return v[index];
    }


}