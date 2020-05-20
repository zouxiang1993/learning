/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/20
 */
public abstract class MathUtil {
    /**
     * 判断x是否是2的整数次幂
     */
    public static boolean isPowerOf2(int x) {
        return (x & (x - 1)) == 0;
    }

    /**
     * 返回不小于x的、最小的、2的整数次幂
     *
     * @param x
     * @return
     */
    public static int minPowerOf2NotLessThan(int x) {
        int n = x - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return n + 1;
    }

}
