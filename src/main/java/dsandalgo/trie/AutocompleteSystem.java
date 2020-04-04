package dsandalgo.trie;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * https://leetcode.com/problems/design-search-autocomplete-system/
 * Design a search autocomplete system for a search engine. Users may input a sentence (at least one word and end with a special character '#'). For each character they type except '#', you need to return the top 3 historical hot sentences that have prefix the same as the part of sentence already typed. Here are the specific rules:
 *
 * The hot degree for a sentence is defined as the number of times a user typed the exactly same sentence before.
 * The returned top 3 hot sentences should be sorted by hot degree (The first is the hottest one). If several sentences have the same degree of hot, you need to use ASCII-code order (smaller one appears first).
 * If less than 3 hot sentences exist, then just return as many as you can.
 * When the input is a special character, it means the sentence ends, and in this case, you need to return an empty list.
 * Your job is to implement the following functions:
 *
 * The constructor function:
 *
 * AutocompleteSystem(String[] sentences, int[] times): This is the constructor. The input is historical data. Sentences is a string array consists of previously typed sentences. Times is the corresponding times a sentence has been typed. Your system should record these historical data.
 *
 * Now, the user wants to input a new sentence. The following function will provide the next character the user types:
 *
 * List<String> input(char c): The input c is the next character typed by the user. The character will only be lower-case letters ('a' to 'z'), blank space (' ') or a special character ('#'). Also, the previously typed sentence should be recorded in your system. The output will be the top 3 historical hot sentences that have prefix the same as the part of sentence already typed.
 *
 *
 * Example:
 * Operation: AutocompleteSystem(["i love you", "island","ironman", "i love leetcode"], [5,3,2,2])
 * The system have already tracked down the following sentences and their corresponding times:
 * "i love you" : 5 times
 * "island" : 3 times
 * "ironman" : 2 times
 * "i love leetcode" : 2 times
 * Now, the user begins another search:
 *
 * Operation: input('i')
 * Output: ["i love you", "island","i love leetcode"]
 * Explanation:
 * There are four sentences that have prefix "i". Among them, "ironman" and "i love leetcode" have same hot degree. Since ' ' has ASCII code 32 and 'r' has ASCII code 114, "i love leetcode" should be in front of "ironman". Also we only need to output top 3 hot sentences, so "ironman" will be ignored.
 *
 * Operation: input(' ')
 * Output: ["i love you","i love leetcode"]
 * Explanation:
 * There are only two sentences that have prefix "i ".
 *
 * Operation: input('a')
 * Output: []
 * Explanation:
 * There are no sentences that have prefix "i a".
 *
 * Operation: input('#')
 * Output: []
 * Explanation:
 * The user finished the input, the sentence "i a" should be saved as a historical sentence in system. And the following input will be counted as a new search.
 *
 *
 * Note:
 *
 * The input sentence will always start with a letter and end with '#', and only one blank space will exist between two words.
 * The number of complete sentences that to be searched won't exceed 100. The length of each sentence including those in the historical data won't exceed 100.
 * Please use double-quote instead of single-quote when you write test cases even for a character input.
 * Please remember to RESET your class variables declared in class AutocompleteSystem, as static/class variables are persisted across multiple test cases. Please see here for more details.
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
