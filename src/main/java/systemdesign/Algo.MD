Some Algorithm Need to know for System Design

https://github.com/resumejob/system-design-algorithms

Frugal Streaming

Geohash / S2 Geometry

Leaky bucket / Token bucket

Loosy Counting

Operational transformation

Quadtree / Rtree

Ray casting

Reverse index

Rsync algorithm

Trie algorithm

Consistent Hashing


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
In distributed system, when we replicate large content among peers, individual chunk need to be hashed and verified for tamper 
or corruption. Merkle tree is a data structure to link chunk hash with the root hash.
The leaves of the tree correspond to hashes of the data chunks of a file, and the parents of these leaves being hashes of the concatenation.

https://www.codementor.io/blog/merkle-trees-5h9arzd3n8
https://hackernoon.com/merkle-tree-what-is-it-and-why-use-it-8m2a63xjd


**B-Tree**


Alon Matias Szegedy


Hierarchical Timing Wheels