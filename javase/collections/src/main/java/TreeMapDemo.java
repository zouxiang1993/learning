import java.util.Map;
import java.util.TreeMap;

/**
 * Author: zouxiang
 * Date: 2020/2/25
 * Description: No Description
 */
public class TreeMapDemo {
    public static void main(String[] args) {
//        Map<Integer, String> map = new HashMap<>();
        Map<Integer, String> map = new TreeMap<>(); // TreeMap会根据key来排序
        map.put(5666, "5666");
        map.put(444, "444");
        map.put(1, "111");
        map.put(333, "333");
        map.put(9999, "9999");

        for (int i : map.keySet()) {
            System.out.println(i);
        }
    }
}
