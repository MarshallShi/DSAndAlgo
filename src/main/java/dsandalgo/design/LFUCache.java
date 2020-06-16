package dsandalgo.design;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/lfu-cache/
 *
 * Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following operations: get and put.
 *
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 * put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity, it should invalidate
 * the least frequently used item before inserting a new item. For the purpose of this problem, when there is a tie (i.e., two or more keys
 * that have the same frequency), the least recently used key would be evicted.
 *
 * Note that the number of times an item is used is the number of calls to the get and put functions for that item since it was inserted.
 * This number is set to zero when the item is removed.
 *
 *
 *
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 *
 */
public class LFUCache {

    private int capacity;
    private int count;
    private Map<Integer, DataNode> data;
    private Map<Integer, DataNode> finalNodes;
    private DataNode dummyHead;
    private DataNode dummyEnd;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        count = 0;
        data = new HashMap<Integer, DataNode>();
        finalNodes = new HashMap<Integer, DataNode>();
        dummyHead = new DataNode(0, 0, 0);
        dummyEnd = new DataNode(0, 0, 0);
        dummyHead.next = dummyEnd;
        dummyEnd.prev = dummyHead;
    }

    public int get(int key) {
        if (capacity == 0 || !data.containsKey(key)) {
            return -1;
        }
        DataNode old = data.get(key);
        put(key, old.value);
        return old.value;
    }

    public void put(int key, int value) {
        if (capacity == 0) {
            return;
        }
        if (data.containsKey(key)) { // this key has appeared
            DataNode cur = data.get(key);
            if (finalNodes.get(cur.times) == cur && finalNodes.get(cur.times + 1) == null) { // the position should not change
                finalNodes.put(cur.times, cur.prev.times == cur.times ? cur.prev : null);
                cur.times++;
                cur.value = value;
                finalNodes.put(cur.times, cur);
                return;
            }
            removeNode(cur); // remove node cur
            if (finalNodes.get(cur.times) == cur) {
                finalNodes.put(cur.times, cur.prev.times == cur.times ? cur.prev : null);
            }
            cur.times++;
            cur.value = value;
            DataNode finalNode = finalNodes.get(cur.times) == null ? finalNodes.get(cur.times - 1) : finalNodes.get(cur.times);
            insertNode(finalNode, cur);
            finalNodes.put(cur.times, cur); // cur is the final node which appeared cur.times
        } else if (count == capacity) { // reach limt of the cache
            DataNode head = dummyHead.next;
            removeNode(head); //remove the first which appeared least times and is the least Used
            data.remove(head.key);
            if (finalNodes.get(head.times) == head) {
                finalNodes.remove(head.times);
            }
            DataNode cur = new DataNode(key, value, 1);
            if (finalNodes.get(1) == null) {
                insertNode(dummyHead, cur);
            } else {
                DataNode finalNode = finalNodes.get(1);
                insertNode(finalNode, cur);
            }
            finalNodes.put(1, cur);
            data.put(key, cur);
        } else {
            count++;
            DataNode cur = new DataNode(key, value, 1);
            if (finalNodes.get(1) == null){
                insertNode(dummyHead, cur);
            } else {
                DataNode finalNode = finalNodes.get(1);
                insertNode(finalNode, cur);
            }
            finalNodes.put(1, cur);
            data.put(key, cur);
        }
    }

    public void insertNode(DataNode t1, DataNode t2) {
        t2.next = t1.next;
        t1.next.prev = t2;
        t1.next = t2;
        t2.prev = t1;
    }

    public void removeNode(DataNode node) {
        node.next.prev = node.prev;
        node.prev.next = node.next;
    }

    class DataNode {
        int key;
        int value;
        int times;
        DataNode prev;
        DataNode next;
        public DataNode(int key, int value, int times) {
            this.key = key;
            this.value = value;
            this.times = times;
        }
    }
}
