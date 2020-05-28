package sequences;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/28
 */
public class Kmp {

    public int indexOf(String text, String pattern) {
        if (text == null) {
            return -1;
        }
        if (pattern == null) {
            return -1;
        }
        if (text.length() == 0) {
            return pattern.length() == 0 ? 0 : -1;
        }
        if (pattern.length() == 0) {
            return 0;
        }
        if (text.length() < pattern.length()) {
            return -1;
        }

        int[] next = getNext(pattern);
        final int tLen = text.length();
        final int pLen = pattern.length();

        int ti = 0;
        int pi = 0;
        while (pi < pLen && ti < tLen) {
            if (pi == -1 || text.charAt(ti) == pattern.charAt(pi)) {
                ti++;
                pi++;
            } else {
                pi = next[pi];
            }
        }
        if (pi >= pattern.length()){
            // 匹配成功
            return ti - pi;
        }else {
            // 匹配失败
            return -1;
        }
    }

    private int[] getNext(String pattern) {
        int[] next = new int[pattern.length()];
        final int pLen = pattern.length();
        int j = 0;
        int k = -1;
        next[0] = -1;
        while (j < pLen - 1) {
            if (k == -1 || pattern.charAt(j) == pattern.charAt(k)) {
                j++;
                k++;
                next[j] = k;
            } else {
                k = next[k];
            }
        }
        return next;
    }

    public static void main(String[] args) {
        Kmp m = new Kmp();
        int result = m.indexOf("aaaaaaaa", "b");
        System.out.println(result);
    }
}
