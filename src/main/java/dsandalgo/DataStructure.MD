Data Structure Design

Gist of those questions are about creating customized linked list, doubly linked list, in combination of hash map to store the reference to 
certain max or min, so we achieve O(1) for most of the operation on the data structure.

https://leetcode.com/problems/lru-cache/

https://leetcode.com/problems/lfu-cache/

    class LFUCache {
    
        class Tuple {
            int key;
            int value;
            int times;
            Tuple prev;
            Tuple next;
            public Tuple(int key, int value, int times) {
                this.key = key;
                this.value = value;
                this.times = times;
            }
        }
        
        private int capacity;
        private int count;
        private HashMap<Integer, Tuple> data; // all cached data.
        private HashMap<Integer, Tuple> finalNodes; // key is the times, value is the node of key times
        private Tuple dummyHead;
        private Tuple dummyEnd;
        
        public LFUCache(int capacity) {
            this.capacity = capacity;
            count = 0;
            data = new HashMap<Integer, Tuple>();
            finalNodes = new HashMap<Integer, Tuple>();
            dummyHead = new Tuple(0, 0, 0);
            dummyEnd = new Tuple(0, 0, 0);
            dummyHead.next = dummyEnd;
            dummyEnd.prev = dummyHead;
        }
        
        public int get(int key) {
            if (capacity == 0 || !data.containsKey(key)) {
                return -1;
            }
            Tuple old = data.get(key);
            put(key, old.value);
            return old.value;
        }
        
        public void put(int key, int value) {
            if (capacity == 0) {
                return;
            }
            if (data.containsKey(key)) { // this key has appeared
                Tuple cur = data.get(key);
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
                Tuple finalNode = finalNodes.get(cur.times) == null ? finalNodes.get(cur.times - 1) : finalNodes.get(cur.times);
                insertNode(finalNode, cur);
                finalNodes.put(cur.times, cur); // cur is the final node whitch appeared cur.times
            } else if (count == capacity) { // reach limt of the cache
                Tuple head = dummyHead.next;
                removeNode(head); //remove the first which appeared least times and is the least Used
                data.remove(head.key);
                if (finalNodes.get(head.times) == head) {
                    finalNodes.remove(head.times);
                }
                Tuple cur = new Tuple(key, value, 1);
                if (finalNodes.get(1) == null) {
                    insertNode(dummyHead, cur);
                } else {
                    Tuple finalNode = finalNodes.get(1);
                    insertNode(finalNode, cur);
                }
                finalNodes.put(1, cur);
                data.put(key, cur);
            } else {
                count++;
                Tuple cur = new Tuple(key, value, 1);
                if (finalNodes.get(1) == null){
                    insertNode(dummyHead, cur);
                } else {
                    Tuple finalNode = finalNodes.get(1);
                    insertNode(finalNode, cur);
                }
                finalNodes.put(1, cur);
                data.put(key, cur);
            }
        }
    
        public void insertNode(Tuple t1, Tuple t2) {
            t2.next = t1.next;
            t1.next.prev = t2;
            t1.next = t2;
            t2.prev = t1;
        }
    
        public void removeNode(Tuple node) {
            node.next.prev = node.prev;
            node.prev.next = node.next;
        }
        
    }

https://leetcode.com/problems/max-stack/

https://leetcode.com/problems/min-stack/

https://leetcode.com/problems/all-oone-data-structure/

    class AllOne {

        // maintain a doubly linked list of Buckets
        private Bucket head;
        private Bucket tail;
        
        // for accessing a specific Bucket among the Bucket list in O(1) time
        private Map<Integer, Bucket> countBucketMap;
        // keep track of count of keys
        private Map<String, Integer> keyCountMap;
    
        // each Bucket contains all the keys with the same count
        private class Bucket {
            int count;
            Set<String> keySet;
            Bucket next;
            Bucket pre;
            public Bucket(int cnt) {
                count = cnt;
                keySet = new HashSet<>();
            }
        }
    
        /** Initialize your data structure here. */
        public AllOne() {
            head = new Bucket(Integer.MIN_VALUE);
            tail = new Bucket(Integer.MAX_VALUE);
            head.next = tail;
            tail.pre = head;
            countBucketMap = new HashMap<>();
            keyCountMap = new HashMap<>();
        }
    
        /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
        public void inc(String key) {
            if (keyCountMap.containsKey(key)) {
                changeKey(key, 1);
            } else {
                keyCountMap.put(key, 1);
                if (head.next.count != 1)
                    addBucketAfter(new Bucket(1), head);
                head.next.keySet.add(key);
                countBucketMap.put(1, head.next);
            }
        }
    
        /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
        public void dec(String key) {
            if (keyCountMap.containsKey(key)) {
                int count = keyCountMap.get(key);
                if (count == 1) {
                    keyCountMap.remove(key);
                    removeKeyFromBucket(countBucketMap.get(count), key);
                } else {
                    changeKey(key, -1);
                }
            }
        }
    
        /** Returns one of the keys with maximal value. */
        public String getMaxKey() {
            return tail.pre == head ? "" : (String) tail.pre.keySet.iterator().next();
        }
    
        /** Returns one of the keys with Minimal value. */
        public String getMinKey() {
            return head.next == tail ? "" : (String) head.next.keySet.iterator().next();
        }
    
        // helper function to make change on given key according to offset
        private void changeKey(String key, int offset) {
            int count = keyCountMap.get(key);
            keyCountMap.put(key, count + offset);
            Bucket curBucket = countBucketMap.get(count);
            Bucket newBucket;
            if (countBucketMap.containsKey(count + offset)) {
                // target Bucket already exists
                newBucket = countBucketMap.get(count + offset);
            } else {
                // add new Bucket
                newBucket = new Bucket(count + offset);
                countBucketMap.put(count + offset, newBucket);
                addBucketAfter(newBucket, offset == 1 ? curBucket : curBucket.pre);
            }
            newBucket.keySet.add(key);
            removeKeyFromBucket(curBucket, key);
        }
    
        private void removeKeyFromBucket(Bucket bucket, String key) {
            bucket.keySet.remove(key);
            if (bucket.keySet.size() == 0) {
                removeBucketFromList(bucket);
                countBucketMap.remove(bucket.count);
            }
        }
    
        private void removeBucketFromList(Bucket bucket) {
            bucket.pre.next = bucket.next;
            bucket.next.pre = bucket.pre;
            bucket.next = null;
            bucket.pre = null;
        }
    
        // add newBucket after preBucket
        private void addBucketAfter(Bucket newBucket, Bucket preBucket) {
            newBucket.pre = preBucket;
            newBucket.next = preBucket.next;
            preBucket.next.pre = newBucket;
            preBucket.next = newBucket;
        }
    }
    
    
    
Add and Search Word - Data structure design
https://leetcode.com/problems/add-and-search-word-data-structure-design/
Trie data structure, also apply backtrack concept for the wild card matching.

    class WordDictionary {
    
        private TrieNode root;
        /** Initialize your data structure here. */
        public WordDictionary() {
            root = new TrieNode();
        }
        
        /** Adds a word into the data structure. */
        public void addWord(String word) {
            if (word == null || word.length() == 0) {
                return;
            }
            TrieNode node = root;
            for (int i = 0; i < word.length(); i++) {
                char cur = word.charAt(i);
                if (node.links[cur - 'a'] == null) {
                    node.links[cur - 'a'] = new TrieNode();
                }
                node = node.links[cur - 'a'];
            }
            node.isEnd = true;
        }
        
        /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
        public boolean search(String word) {
            TrieNode node = root;
            char[] arr = word.toCharArray();
            if (match(arr, 0, root)) {
                return true;
            }
            return false;
        }
        
        private boolean match(char[] chs, int k, TrieNode node) {
            if (k == chs.length) {
                return node.isEnd;
            }
            if (chs[k] != '.') {
                return node.links[chs[k] - 'a'] != null && match(chs, k + 1, node.links[chs[k] - 'a']);
            } else {
                for (int i = 0; i < node.links.length; i++) {
                    if (node.links[i] != null) {
                        if (match(chs, k + 1, node.links[i])) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        
        class TrieNode {
            TrieNode[] links;
            boolean isEnd;
            TrieNode() {
                links = new TrieNode[26];
                isEnd = false;
            }
        }
    }


Insert Delete GetRandom O(1) - Duplicates allowed
https://leetcode.com/problems/insert-delete-getrandom-o1-duplicates-allowed/
By using ArrayList and HashMap, we can achieve O(1) easily for insert, and also support duplicate, also random access.
Tricky part is the remove, removing from a random index in ArrayList won't be O(1), need to remove from the last.

    class RandomizedCollection {
    
        //Use lst to store all the inserted data, allow duplicates.
        private List<Integer> lst;
        //Use map and set to store the indexes of the data.
        private Map<Integer, Set<Integer>> idx;
        private java.util.Random rand = new java.util.Random();
        /** Initialize your data structure here. */
    
        public RandomizedCollection() {
            lst = new ArrayList<Integer>();
            idx = new HashMap<Integer, Set<Integer>>();
        }
    
        /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
        public boolean insert(int val) {
            if (!idx.containsKey(val)) {
                idx.put(val, new LinkedHashSet<Integer>());
            }
            idx.get(val).add(lst.size());
            lst.add(val);
            return idx.get(val).size() == 1;
        }
    
        /** Removes a value from the collection. Returns true if the collection contained the specified element. */
        //Trick: use linkedhashset to get the first idx for the data
        //always remove the last element in both set and list that's the only O(1) allowed.
        public boolean remove(int val) {
            if (!idx.containsKey(val) || idx.get(val).size() == 0) {
                return false;
            }
            //Find the index to remove from the lst.
            int remove_idx = idx.get(val).iterator().next();
            idx.get(val).remove(remove_idx);
            //Can't remove directly because of the O(1) requirement, need to swap and remove the last element.
            int last = lst.get(lst.size() - 1);
            lst.set(remove_idx, last);
            idx.get(last).add(remove_idx);
            idx.get(last).remove(lst.size() - 1);
            lst.remove(lst.size() - 1);
            return true;
        }
    
        /** Get a random element from the collection. */
        public int getRandom() {
            return lst.get(rand.nextInt(lst.size()));
        }
    }
    

Design In-Memory File System
https://leetcode.com/problems/design-in-memory-file-system/
Nested data structure to support File definition, similiar to the Tree structure.
    
    class File {
        boolean isFile = false;
        Map<String, File> children = new HashMap<>();
        String content = "";
    }

    File root;

    public FileSystem() {
        root = new File();
    }

    public List<String> ls(String path) {
        String[] dirs = path.split("/");
        File node = root;
        String name = "";
        List<String> res = new ArrayList<String>();
        for (String dir : dirs) {
            if (dir.length() == 0) continue;
            if (!node.children.containsKey(dir)) {
                return res;
            }
            node = node.children.get(dir);
            name = dir;
        }
        if (node.isFile) {
            res.add(name);
        } else {
            for (String str : node.children.keySet()) {
                res.add(str);
            }
            Collections.sort(res);
        }
        return res;
    }

    public void mkdir(String path) {
        traverse(path);
    }

    public void addContentToFile(String filePath, String content) {
        File cur = traverse(filePath);
        cur.isFile = true;
        cur.content += content;
    }

    public String readContentFromFile(String filePath) {
        File cur = traverse(filePath);
        return cur.content;
    }

    private File traverse(String filePath) {
        String[] dirs = filePath.split("/");
        File cur = root;
        for (String dir : dirs) {
            if (dir.length() == 0) {
                continue;
            }
            cur.children.putIfAbsent(dir, new File());
            cur = cur.children.get(dir);
        }
        return cur;
    }
    
