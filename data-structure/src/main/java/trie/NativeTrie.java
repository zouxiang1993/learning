package trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Description: 朴素的字典树
 *
 * @author zouxiang
 * @date 2020/5/28
 */
public class NativeTrie {

    private Node root;

    public NativeTrie() {
        this.root = new Node();
    }

    public boolean matchExact(String text) {
        if (text == null) {
            return false;
        }
        Node current = this.root;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            current = current.children.get(ch);
            if (current == null) {
                return false;
            }
        }
        return current.isFinal;
    }

    /**
     * 添加一个模式串
     *
     * @param pattern
     */
    public void add(String pattern) {
        if (pattern == null) {
            return;
        }
        Node current = this.root;
        for (int i = 0; i < pattern.length(); i++) {
            char ch = pattern.charAt(i);
            Node next = current.children.get(ch);
            if (next == null) {
                next = new Node();
                next.ch = ch;
                current.children.put(ch, next);
            }
            current = next;
        }
        current.isFinal = true;
    }

    class Node {
        char ch;
        boolean isFinal = false;
        Map<Character, Node> children = new HashMap<>();
    }

    public static void main(String[] args) {
        NativeTrie trie = new NativeTrie();
        trie.add("abc");
        trie.add("abcdef");
        trie.add("aaaa");
        trie.add("bbb");

        System.out.println(trie.matchExact("a"));
        System.out.println(trie.matchExact("ab"));
        System.out.println(trie.matchExact("abc"));
        System.out.println(trie.matchExact("abcd"));
        System.out.println(trie.matchExact("abcde"));
        System.out.println(trie.matchExact("abcdef"));

        System.out.println(trie.matchExact(""));
        System.out.println(trie.matchExact("aaaa"));
        System.out.println(trie.matchExact("aaaaa"));

    }
}
