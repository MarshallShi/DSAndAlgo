package dsandalgo.trie;

import java.util.ArrayList;
import java.util.List;

public class TrieExe2 {

    public static void main(String[] args) {
        TrieExe2 exe = new TrieExe2();
        String[] dict = {"havana"};
        char[][] board = {
                {'o','a','a','n'},
                {'e','t','a','e'},
                {'i','h','k','r'},
                {'i','f','l','v'}};
        char[][] board1 = {
                {'a','b'},
                {'c','d'}};
        String[] words = {"oath","pea","eat","rain"};
        String[] words1 = {"acdb"};
        System.out.println(exe.expand("{a,b}c{d,e}f"));
    }

    /**
     * https://leetcode.com/problems/brace-expansion/
     *
     * A string S represents a list of words.
     *
     * Each letter in the word has 1 or more options.  If there is one option, the letter is represented as is.
     * If there is more than one option, then curly braces delimit the options.  For example, "{a,b,c}" represents options ["a", "b", "c"].
     *
     * For example, "{a,b,c}d{e,f}" represents the list ["ade", "adf", "bde", "bdf", "cde", "cdf"].
     *
     * Return all words that can be formed in this manner, in lexicographical order.
     *
     *
     *
     * Example 1:
     *
     * Input: "{a,b}c{d,e}f"
     * Output: ["acdf","acef","bcdf","bcef"]
     * Example 2:
     *
     * Input: "abcd"
     * Output: ["abcd"]
     *
     */
    class TrieNodeEXP {
        TrieNodeEXP[] links;
        String word;
        TrieNodeEXP() {
            links = new TrieNodeEXP[26];
        }
    }
    private TrieNodeEXP rootEXP;
    public String[] expand(String S) {
        rootEXP = new TrieNodeEXP();
        rootEXP.word = "";
        int idx = 0;
        while (idx < S.length()) {
            List<Character> charr = new ArrayList<Character>();
            if (S.charAt(idx) == '{') {
                idx++;
                while (S.charAt(idx) != '}') {
                    if (S.charAt(idx) != ',') {
                        charr.add(S.charAt(idx));
                    }
                    idx++;
                }
                idx++;
            } else {
                charr.add(S.charAt(idx));
                idx++;
            }
            insertIntoTrie(rootEXP, charr);
        }
        List<String> ans = new ArrayList<String>();
        findAnswers(rootEXP, ans);
        String[] ret = new String[ans.size()];
        for (int i=0; i<ans.size(); i++) {
            ret[i] = ans.get(i);
        }
        return ret;
    }

    private void insertIntoTrie(TrieNodeEXP node, List<Character> charr) {
        if (node.word != null) {
            String temp = node.word;
            for (int i = 0; i<charr.size(); i++) {
                node.links[charr.get(i) - 'a'] = new TrieNodeEXP();
                node.links[charr.get(i) - 'a'].word = temp + String.valueOf(charr.get(i));
            }
            node.word = null;
        } else {
            for (int i=0; i<26; i++) {
                if (node.links[i] != null) {
                    insertIntoTrie(node.links[i], charr);
                }
            }
        }
    }

    private void findAnswers(TrieNodeEXP node, List<String> ans) {
        if (node.word != null) {
            ans.add(node.word);
            return;
        } else {
            for (int i=0; i<26; i++) {
                if (node.links[i] != null) {
                    findAnswers(node.links[i], ans);
                }
            }
        }
    }



    class TrieNodeLS {
        TrieNodeLS[] links;
        boolean isEnd;
        TrieNodeLS() {
            links = new TrieNodeLS[26];
            isEnd = false;
        }
    }
    private TrieNodeLS root;

    public String lastSubstring(String s) {
        if (s.indexOf("z") != -1) {
            return s.substring(s.indexOf("z"));
        }
        root = new TrieNodeLS();
        for (int i=0; i<s.length(); i++) {
            createTrieNode(s, i, root);
        }
        StringBuilder sb = new StringBuilder();
        TrieNodeLS node = root;
        while (!node.isEnd) {
            for (int i=25; i>=0; i--) {
                if (node.links[i] != null) {
                    sb.append((char)('a' + i));
                    node = node.links[i];
                    break;
                }
            }
        }
        return sb.toString();
    }

    private void createTrieNode(String s, int startIdx, TrieNodeLS root) {
        TrieNodeLS node = root;
        for (int i = startIdx; i<s.length(); i++) {
            if (node.links[s.charAt(i) - 'a'] == null) {
                node.links[s.charAt(i) - 'a'] = new TrieNodeLS();
            } else {
                node.isEnd = false;
            }
            node = node.links[s.charAt(i) - 'a'];
        }
        node.isEnd = true;
    }
}
