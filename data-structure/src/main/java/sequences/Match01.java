package sequences;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/28
 */
public class Match01 {
    // 文本串 : text
    // 模式串 : pattern
    // 求pattern在text中出现的位置。

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

        final int tLen = text.length();
        final int pLen = pattern.length();

        int ti = 0;
        int pi = 0;
        while (pi < pLen && ti < tLen) {
            if (text.charAt(ti) == pattern.charAt(pi)) {
                ti++;
                pi++;
            } else {
                ti -= pi - 1;
                pi = 0;
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

    public static void main(String[] args) {
        Match01 m = new Match01();
        int result = m.indexOf("aaaaaa", "b");
        System.out.println(result);
    }
}
