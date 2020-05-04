package dsandalgo.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/serialize-and-deserialize-n-ary-tree/
 * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be stored in a file or memory buffer,
 * or transmitted across a network connection link to be reconstructed later in the same or another computer environment.
 *
 * Design an algorithm to serialize and deserialize an N-ary tree. An N-ary tree is a rooted tree in which each node has no more than N children.
 * There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that an N-ary tree can be serialized
 * to a string and this string can be deserialized to the original tree structure.
 *
 * For example, you may serialize the following 3-ary tree
 *
 *
 *
 * as [1 [3[5 6] 2 4]]. Note that this is just an example, you do not necessarily need to follow this format.
 *
 * Or you can follow LeetCode's level order traversal serialization format, where each group of children is separated by the null value.
 *
 *
 *
 * For example, the above tree may be serialized as [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14].
 *
 * You do not necessarily need to follow the above suggested formats, there are many more different formats that work so please be creative
 * and come up with different approaches yourself.
 *
 * Constraints:
 * The height of the n-ary tree is less than or equal to 1000
 * The total number of nodes is between [0, 10^4]
 * Do not use class member/global/static variables to store states. Your encode and decode algorithms should be stateless.
 */
public class NAryTreeCodec {

    public static void main(String[] args) {
        NAryTreeCodec codec = new NAryTreeCodec();
        Node node = codec.createTestNode();
        String str = codec.serialize(node);
        System.out.println(str);
        Node dode = codec.deserialize(str);
        System.out.println(dode);
        System.out.println(codec.serialize(dode));
    }

    private Node createTestNode() {
        Node node = new Node(1);

        node.children = new ArrayList<Node>();

        Node node3 = new Node(3);
        Node node2 = new Node(2);
        Node node4 = new Node(4);


        node.children.add(node2);
        node.children.add(node4);
        node.children.add(node3);

        node3.children = new ArrayList<>();
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        node3.children.add(node5);
        node3.children.add(node6);
        return node;
    }

    class Node {
        public int val;
        public List<Node> children;
        public Node() {}
        public Node(int _val) {
            val = _val;
        }
        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }

    // Encodes a tree to a single string.
    public String serialize(Node root) {
        if (root == null) {
            return null;
        }
        return "[" + serializeHelper(root) + "]";
    }

    private String serializeHelper(Node root) {
        if (root.children == null) {
            return String.valueOf(root.val);
        } else {
            String ret = String.valueOf(root.val) + "[";
            int numOfChildren = root.children.size();
            for (int i=0; i<numOfChildren; i++) {
                Node childNode = root.children.get(i);
                ret = ret + serializeHelper(childNode);
                if (i != numOfChildren-1) {
                    ret = ret + " ";
                }
            }
            ret = ret + "]";
            return ret;
        }
    }

    // Decodes your encoded data to tree.
    public Node deserialize(String data) {
        if (data == null || data.length() == 0) {
            return null;
        }
        return deserializeHelper(data.substring(1, data.length()-1));
    }

    private Node deserializeHelper(String data) {
        if (data.indexOf("[") == -1) {
            return new Node(Integer.parseInt(data));
        } else {
            int firstSpaceIdx = data.indexOf("[");
            int nodeVal = Integer.parseInt(data.substring(0, firstSpaceIdx));
            Node node = new Node(nodeVal);
            List<Node> children = new ArrayList<Node>();
            int counter = 0;
            int prev = firstSpaceIdx+1;
            for (int i=firstSpaceIdx+1; i<data.length()-1; i++) {
                if (data.charAt(i) == '[') {
                    counter++;
                }
                if (data.charAt(i) == ']') {
                    counter--;
                }
                if (data.charAt(i) == ' ' && counter == 0) {
                    children.add(deserializeHelper(data.substring(prev, i)));
                    prev = i+1;
                }
                if (i == data.length()-2) {
                    children.add(deserializeHelper(data.substring(prev, data.length()-1)));
                }
            }
            node.children = children;
            return node;
        }
    }
}
