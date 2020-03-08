package dsandalgo.trie;

public class MagicDictionary {

    class TrieNode {
        TrieNode[] links;
        boolean isEnd;
        TrieNode() {
            links = new TrieNode[26];
            isEnd = false;
        }
    }

    /** Initialize your data structure here. */
    private TrieNode tire;

    public MagicDictionary() {
        tire = new TrieNode();
    }

    /** Build a dictionary through a list of words */
    public void buildDict(String[] dict) {
        for (String word : dict) {
            char[] arr = word.toCharArray();
            TrieNode root = tire;
            for (int i=0; i<arr.length; i++) {
                if (root.links[arr[i]-'a'] == null) {
                    TrieNode node = new TrieNode();
                    root.links[arr[i]-'a'] = node;
                }
                root = root.links[arr[i]-'a'];
            }
            root.isEnd = true;
        }
    }

    private boolean exist(String word) {
        char[] arr = word.toCharArray();
        TrieNode root = tire;
        for (int i=0; i<arr.length; i++) {
            if (root.links[arr[i]-'a'] == null) {
                return false;
            } else {
                root = root.links[arr[i]-'a'];
            }
        }
        return root.isEnd;
    }

    /** Returns if there is any word in the trie that equals to the given word after modifying exactly one character */
    public boolean search(String word) {
        char[] arr = word.toCharArray();
        for (int i=0; i<arr.length; i++) {
            char org = arr[i];
            for (int j=0; j<26; j++) {
                if (org != (char)(j+'a')) {
                    arr[i] = (char)(j+'a');
                    String nword = new String(arr);
                    if (exist(nword)) {
                        return true;
                    }
                }
            }
            arr[i] = org;
        }
        return false;
    }

    public static void main(String[] args) {
        MagicDictionary md = new MagicDictionary();
        String[] dict = {"hello", "leetcode"};
        md.buildDict(dict);
        System.out.println(md.search("hello"));
        System.out.println(md.search("hhllo"));
        System.out.println(md.search("leetcoded"));
        System.out.println(md.search("hellow"));
    }
}
