package dsandalgo.tree;

import java.util.LinkedList;
import java.util.List;


//https://en.wikipedia.org/wiki/Threaded_binary_tree
//https://www.educative.io/edpresso/what-is-morris-traversal
//https://www.cnblogs.com/AnnieKim/archive/2013/06/15/morristraversal.html
//https://leetcode.com/problems/recover-binary-search-tree/discuss/32559/Detail-Explain-about-How-Morris-Traversal-Finds-two-Incorrect-Pointer

public class MorrisTraversal {

    //Standard morris inorder traverse.

    /**
     * 1. 如果当前节点的左孩子为空，则输出当前节点并将其右孩子作为当前节点。
     *
     * 2. 如果当前节点的左孩子不为空，在当前节点的左子树中找到当前节点在中序遍历下的前驱节点。
     *
     *    a) 如果前驱节点的右孩子为空，将它的右孩子设置为当前节点。当前节点更新为当前节点的左孩子。
     *
     *    b) 如果前驱节点的右孩子为当前节点，将它的右孩子重新设为空（恢复树的形状）。输出当前节点。当前节点更新为当前节点的右孩子。
     *
     * 3. 重复以上1、2直到当前节点为空。
     */
    public void morrisTraversal(TreeNode root){
        TreeNode temp = null;
        while(root!=null){
            if(root.left!=null){
                // connect threading for root
                temp = root.left;
                while(temp.right!=null && temp.right != root)
                    temp = temp.right;
                // the threading already exists
                if(temp.right!=null){
                    temp.right = null;
                    System.out.println(root.val);
                    root = root.right;
                }else{
                    // construct the threading
                    temp.right = root;
                    root = root.left;
                }
            }else{
                System.out.println(root.val);
                root = root.right;
            }
        }
    }

    /**
     * https://leetcode.com/problems/recover-binary-search-tree/
     *
     * @param root
     */
    public void recoverTree_threaded_binary_tree(TreeNode root) {
        List<TreeNode> eNodes = new LinkedList<TreeNode>(); //error nodes
        if(root == null) return;
        TreeNode current = root;
        TreeNode pre;
        TreeNode previous = null;
        while(current != null){

            if(current.left == null){

                if(previous!=null && previous.val > current.val){
                    eNodes.add(previous);
                    eNodes.add(current);
                }
                previous = current;
                current = current.right;

            }else{
                pre = current.left;
                while(pre.right != null && pre.right.val != current.val){
                    pre = pre.right;
                }

                if(pre.right == null){
                    pre.right = current;
                    current = current.left;
                }else {
                    if(previous!=null && previous.val > current.val){
                        eNodes.add(previous);
                        eNodes.add(current);
                    }

                    pre.right = null;
                    previous = current;
                    current = current.right;
                }
            }
        }

        //this is redundant check
        //if(eNodes.size() == 0) return;

        if(eNodes.size() == 2){
            pre = eNodes.get(0);
            current = eNodes.get(1);
        }else{ //this case where eNodes.size()==4
            pre = eNodes.get(0);
            current = eNodes.get(3);
        }

        int temp = pre.val;
        pre.val = current.val;
        current.val = temp;
    }
}
