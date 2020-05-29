package trie.dat;

import java.util.Arrays;
import java.util.List;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/28
 */
public class Test {
    public static void main(String[] args) {
        Builder builder = new Builder();
        List<String> patterns = Arrays.asList(
                "一举",
                "一举一动",
                "一举成名",
                "一举成名天下知",
                "万能",
                "万能胶"
        );
        DoubleArrayTrie dat = builder.build(patterns, null);
        System.out.println(dat);
    }
}
