package dsandalgo.trie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/design-search-autocomplete-system/
 *
 */
public class AutocompleteSystem {

    //"i love you","island","iroman","i love leetcode"
    public static void main(String[] args) {
        String[] words = {"i love you","island","iroman","i love leetcode"};
        int[] times = {5,3,2,2};
        AutocompleteSystem auto = new AutocompleteSystem(words, times);
        auto.input('i');
        auto.input(' ');
        auto.input('l');
        auto.input('#');
    }
    class TrieNode {
        TrieNode[] children;
        String content;
        int priority;
        public TrieNode() {
            children = new TrieNode[27]; // 'a' - 'z' and ' '. ' ' will be put into the last element of the array.
            content = null;
            priority = 0;
        }
    }

    public StringBuilder curInput;
    public TrieNode root;

    public AutocompleteSystem(String[] sentences, int[] times) {
        curInput = new StringBuilder();
        root = new TrieNode();
        for (int i=0; i<sentences.length; i++) {
            insert(sentences[i], times[i]);
        }
    }
    public void insert(String word, int time) {
        TrieNode node = root;
        for(char c : word.toCharArray()){
            int index = c - 'a';
            if (c == ' ') {
                index = 26;
            } else {
                index = c - 'a';
            }
            if(node.children[index] == null){
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        if (node.content != null) {
            node.priority = node.priority + 1;
        } else {
            node.content = word;
            node.priority = time;
        }
    }

    public List<String> input(char c) {
        List<String> lst = new ArrayList<String>();
        if (c == '#') {
            insert(curInput.toString(), 1);
            curInput = new StringBuilder();
        } else {
            curInput.append(c);
            lst = search(curInput.toString());
        }
        return lst;
    }

    private List<String> search(String key) {
        PriorityQueue<TrieNode> pq = new PriorityQueue<TrieNode>(new Comparator<TrieNode>() {
            @Override
            public int compare(TrieNode o1, TrieNode o2) {
                if (o1.priority == o2.priority) {
                    return o1.content.compareTo(o2.content);
                } else {
                    return o2.priority - o1.priority;
                }
            }
        });
        TrieNode node = root;
        List<String> ret = new ArrayList<String>();
        for (int i=0; i<key.length(); i++) {
            int idx = 0;
            if (key.charAt(i) == ' ') {
                idx = 26;
            } else {
                idx = key.charAt(i) - 'a';
            }
            if (node.children[idx] != null) {
                node = node.children[idx];
            } else {
                return ret;
            }
        }
        dfsHelper(node, pq);
        int count = 0;
        while (!pq.isEmpty() && count < 3) {
            TrieNode retOne = pq.poll();
            ret.add(retOne.content);
            count++;
        }
        return ret;
    }

    private void dfsHelper(TrieNode startNode, PriorityQueue<TrieNode> pq) {
        if (startNode.content != null) {
            pq.offer(startNode);
        }
        for (int i = 0; i<27; i++) {
            if (startNode.children[i] != null) {
                dfsHelper(startNode.children[i], pq);
            }
        }
    }
}
