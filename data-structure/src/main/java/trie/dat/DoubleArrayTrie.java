package trie.dat;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/28
 */
public class DoubleArrayTrie<V> {
    private int[] base;
    private int[] check;

    DoubleArrayTrie(int[] base, int[] check){
        this.base = base;
        this.check = check;
    }

    /**
     * 双数组的长度
     *
     * @return
     */
    public int arrayLength() {
        return base.length;
    }
}
