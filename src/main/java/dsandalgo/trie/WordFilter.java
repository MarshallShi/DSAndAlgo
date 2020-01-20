package dsandalgo.trie;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * https://leetcode.com/problems/prefix-and-suffix-search/
 * Given many words, words[i] has weight i.
 *
 * Design a class WordFilter that supports one function, WordFilter.f(String prefix, String suffix).
 * It will return the word with given prefix and suffix with maximum weight. If no word exists, return -1.
 *
 * Examples:
 *
 * Input:
 * WordFilter(["apple"])
 * WordFilter.f("a", "e") // returns 0
 * WordFilter.f("b", "") // returns -1
 *
 *
 * Note:
 *
 * words has length in range [1, 15000].
 * For each test case, up to words.length queries WordFilter.f may be made.
 * words[i] has length in range [1, 10].
 * prefix, suffix have lengths in range [0, 10].
 * words[i] and prefix, suffix queries consist of lowercase letters only.
 */
public class WordFilter {

    /*class WFTrieNode {
        WFTrieNode[] children;
        boolean isWord = false;
        int priority = -1;
        WFTrieNode() {
            children = new WFTrieNode[27]; // 'a' - 'z' and '{'. 'z' and '{' are neighbours in ASCII table
        }
    }

    WFTrieNode root;

    public WordFilter(String[] words) {
        root = new WFTrieNode();
        insert(words);
    }

    public void insert(String[] words) {
        for (int i=0; i<words.length; i++) {
            WFTrieNode node = root;
            String word = words[i];
            for(char c : word.toCharArray()){
                int index = c - 'a';
                if(node.children[index] == null){
                    node.children[index] = new WFTrieNode();
                }
                node = node.children[index];
            }
            node.isWord = true;
            node.priority = i;
        }
    }

    public int f(String prefix, String suffix) {
        WFTrieNode node = root;
        WFTrieNode startFrom = root;
        for (int i=0; i<prefix.length(); i++) {
            char pre = prefix.charAt(i);
            if (node != null && node.children[pre - 'a'] != null) {
                node = node.children[pre - 'a'];
                startFrom = node;
            } else {
                return -1;
            }
        }
        Set<Integer> prefixMatching = search(startFrom, "");
        Set<Integer> suffixMatching = search(root, suffix);
        if (prefixMatching.isEmpty() || suffixMatching.isEmpty()) {
            return -1;
        } else {
            int maxPriority = -1;
            for (Integer val : prefixMatching) {
                if (suffixMatching.contains(val)) {
                    maxPriority = Math.max(val, maxPriority);
                }
            }
            return maxPriority;
        }
    }

    private Set<Integer> search(WFTrieNode startFrom, String suffix) {
        Set<Integer> set = new HashSet<Integer>();
        Queue<WFTrieNode> queue = new LinkedList<WFTrieNode>();
        queue.offer(startFrom);
        while (!queue.isEmpty()) {
            WFTrieNode origin = queue.poll();
            boolean isTheNodeRight = true;
            WFTrieNode node = origin;
            for (int i=0; i<suffix.length(); i++) {
                char ch = suffix.charAt(i);
                if (node.children[ch - 'a'] != null) {
                    node = node.children[ch - 'a'];
                } else {
                    isTheNodeRight = false;
                    break;
                }
            }
            if (isTheNodeRight && node.isWord) {
                set.add(node.priority);
            }
            for (WFTrieNode one : origin.children) {
                if (one != null) {
                    queue.offer(one);
                }
            }
        }
        return set;
    }*/

    class TrieNode {
        TrieNode[] children;
        int weight;
        public TrieNode() {
            children = new TrieNode[27]; // 'a' - 'z' and '{'. 'z' and '{' are neighbours in ASCII table
            weight = 0;
        }
    }

    TrieNode root;
    public WordFilter(String[] words) {
        root = new TrieNode();
        for (int weight = 0; weight < words.length; weight++) {
            String word = words[weight] + "{";
            for (int i = 0; i < word.length(); i++) {
                TrieNode cur = root;
                cur.weight = weight;
                // add "apple{apple", "pple{apple", "ple{apple", "le{apple", "e{apple", "{apple" into the Trie Tree
                for (int j = i; j < 2 * word.length() - 1; j++) {
                    int k = word.charAt(j % word.length()) - 'a';
                    if (cur.children[k] == null)
                        cur.children[k] = new TrieNode();
                    cur = cur.children[k];
                    cur.weight = weight;
                }
            }
        }
    }
    public int f(String prefix, String suffix) {
        TrieNode cur = root;
        for (char c: (suffix + '{' + prefix).toCharArray()) {
            if (cur.children[c - 'a'] == null) {
                return -1;
            }
            cur = cur.children[c - 'a'];
        }
        return cur.weight;
    }

    public static void main(String[] args) {
        String[] strs = {"abbbababbb","baaabbabbb","abababbaaa","abbbbbbbba","bbbaabbbaa","ababbaabaa","baaaaabbbb","babbabbabb","ababaababb","bbabbababa"};
        WordFilter wf = new WordFilter(strs);
        System.out.println(wf.f("",""));
        System.out.println(wf.f("","p"));
        System.out.println(wf.f("p",""));
    }

}
