Some Algorithm Need to know for System Design

https://github.com/resumejob/system-design-algorithms

Frugal Streaming

Geohash / S2 Geometry

Leaky bucket / Token bucket

Loosy Counting

Quadtree
Quadtrees are trees used to efficiently store data of points on a two-dimensional space. In this tree, each node has at most four children.
We can construct a quadtree from a two-dimensional area using the following steps:

1.Divide the current two dimensional space into four boxes.
2.If a box contains one or more points in it, create a child object, storing in it the two dimensional space of the box
3.If a box does not contain any points, do not create a child for it
4.Recurse for each of the children.

Quadtrees are used in image compression, where each node contains the average colour of each of its children. 
The deeper you traverse in the tree, the more the detail of the image.
Quadtrees are also used in searching for nodes in a two-dimensional area. 
For instance, if you wanted to find the closest point to given coordinates, you can do it using quadtrees.

Reverse index
It is a data structure that stores mapping from words to documents or set of documents i.e. directs you from word to document.
https://www.geeksforgeeks.org/difference-inverted-index-forward-index/?ref=rp

Rsync algorithm
rsync is a tool that compares directories when copying and is a much more efficient way of backing up data on a regular basis. 
It will compare any changes or differences with the source directory and the repository and then only transfer those differences.
https://www.cs.tufts.edu/~nr/rsync.html

Consistent Hashing
Consistent Hashing is a distributed hashing scheme that operates independently of the number of servers or objects in a distributed 
hash table by assigning them a position on an abstract circle, or hash ring. 
This allows servers and objects to scale without affecting the overall system.
https://www.toptal.com/big-data/consistent-hashing
http://tom-e-white.com/2007/11/consistent-hashing.html
https://www.toptal.com/big-data/consistent-hashing
https://medium.com/@dgryski/consistent-hashing-algorithmic-tradeoffs-ef6b8e2fcae8

Probabilistic data structures can't give you a definite answer, instead they provide you with a reasonable approximation 
of the answer and a way to approximate this estimation. They are extremely useful for big data and streaming application 
because they allow to dramatically decrease the amount of memory needed (in comparison to data structures that give 
you exact answers).

In majority of the cases these data structures use hash functions to randomize the items. 
Because they ignore collisions they keep the size constant, but this is also a reason why they can't give you exact values. 
The advantages they bring:
1) they use small amount of memory (you can control how much)
2) they can be easily parallelizable (hashes are independent)
3) they have constant query time (not even amortized constant like in dictionary)

Usage and some Probabilistic data structures:
Membership querying (Bloom filter, Counting Bloom filter, Quotient filter, Cuckoo filter).
Cardinality (Linear counting, probabilistic counting, LogLog, HyperLogLog, HyperLogLog++).
Frequency (Majority algorithm, Frequent, Count Sketch, Count-Min Sketch).
Rank (Random sampling, q-digest, t-digest).
Similarity (LSH, MinHash, SimHash).

Frequently used probabilistic data structures are boolean filter, hyperloglog, count-min sketch.


**Bloom Filters**
A Bloom filter is a data structure designed to tell you, rapidly and memory-efficiently, whether an element is present in a set.
The price paid for this efficiency is that a Bloom filter is a probabilistic data structure: it tells us that the element 
either definitely is not in the set or may be in the set.
https://llimllib.github.io/bloomfilter-tutorial/
https://en.wikipedia.org/wiki/Bloom_filter
https://blog.cloudflare.com/when-bloom-filters-dont-bloom/
https://www.cs.cmu.edu/~dga/papers/cuckoo-conext2014.pdf


**HyperLogLog**
HyperLogLog algorithm estimates how many unique items are in a list. Works well with large set of data.
http://algo.inria.fr/flajolet/Publications/FlFuGaMe07.pdf
https://engineering.fb.com/data-infrastructure/hyperloglog/


**Count-Min Sketch and Skip Lists**
https://stackoverflow.com/questions/6811351/explaining-the-count-sketch-algorithm/35356116#35356116



**Merkle trees**
Real world examples: 
1. Git
How to track file changes in the tree structure? File parent store the hash of children. If one file in leaf changed, will be propogated up.
2. Amazon DynaDB
3. Blockchain
In distributed system, when we replicate large content among peers, individual chunk need to be hashed and verified for tamper 
or corruption. Merkle tree is a data structure to link chunk hash with the root hash.
The leaves of the tree correspond to hashes of the data chunks of a file, and the parents of these leaves being hashes of the concatenation.

https://www.codementor.io/blog/merkle-trees-5h9arzd3n8
https://hackernoon.com/merkle-tree-what-is-it-and-why-use-it-8m2a63xjd



**B-Tree**


Alon Matias Szegedy


Hierarchical Timing Wheels


Map Reduce Framework for big data process
https://www.youtube.com/watch?v=3zCK_5U69A8
Word Count:
1. Split the data during save into multiple nodes.
2. Map the date to basic values.
3. Shuffle them, move the same kind to the same node for reducing processing.
4. Reduce, which is the logic to aggregate, in case of word count, the count being merged here.
5. Merge the result.