package dsandalgo.trie;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.com/problems/stream-of-characters/
 */

public class StreamChecker {

    class TrieNode {
        TrieNode[] nodes;
        boolean isWord;

        TrieNode() {
            nodes = new TrieNode[26];
            isWord = false;
        }
    }

    TrieNode root = null;
    Queue<TrieNode> validNode = null;

    public StreamChecker(String[] words) {
        root = new TrieNode();
        validNode = new LinkedList<TrieNode>();
        for (String str : words) {
            insert(str);
        }
        validNode.offer(root);
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (node.nodes[index] == null) {
                node.nodes[index] = new TrieNode();
            }
            node = node.nodes[index];
        }
        node.isWord = true;
    }

    public boolean query(char letter) {
        boolean res = false;
        int n = validNode.size();
        for (int i = 0; i < n; i++) {
            TrieNode node = validNode.poll();
            if (node.nodes[letter - 'a'] != null) {
                validNode.offer(node.nodes[letter - 'a']);
                if (node.nodes[letter - 'a'].isWord) {
                    res = true;
                }
            }
            if (node == root) {
                validNode.add(root);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String[] input = {"cd","f","kl"};
        StreamChecker streamChecker = new StreamChecker(input); // init the dictionary.
        System.out.println(streamChecker.query('a'));          // return false
        System.out.println(streamChecker.query('b'));          // return false
        System.out.println(streamChecker.query('c'));          // return false
        System.out.println(streamChecker.query('d'));          // return true, because 'cd' is in the wordlist
        System.out.println(streamChecker.query('e'));          // return false
        System.out.println(streamChecker.query('f'));          // return true, because 'f' is in the wordlist
        System.out.println(streamChecker.query('g'));          // return false
        System.out.println(streamChecker.query('h'));          // return false
        System.out.println(streamChecker.query('i'));          // return false
        System.out.println(streamChecker.query('j'));          // return false
        System.out.println(streamChecker.query('k'));          // return false
        System.out.println(streamChecker.query('l'));          // return true, because 'kl' is in the wordlist
    }
}
