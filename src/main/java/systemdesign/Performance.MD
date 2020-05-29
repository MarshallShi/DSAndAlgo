
http://highscalability.com/numbers-everyone-should-know
http://highscalability.com/blog/2011/1/26/google-pro-tip-use-back-of-the-envelope-calculations-to-choo.html

L1 cache reference 0.5 ns
Branch mispredict 5 ns
L2 cache reference 7 ns
Mutex lock/unlock 100 ns
Main memory reference 100 ns
Compress 1K bytes with Zippy 10,000 ns
Send 2K bytes over 1 Gbps network 20,000 ns
Read 1 MB sequentially from memory 250,000 ns
Round trip within same datacenter 500,000 ns
Disk seek 10,000,000 ns
Read 1 MB sequentially from network 10,000,000 ns
Read 1 MB sequentially from disk 30,000,000 ns
Send packet CA->Netherlands->CA 150,000,000 ns


Writes are 40 times more expensive than reads.
Global shared data is expensive. This is a fundamental limitation of distributed systems. 
The lock contention in shared heavily written objects kills performance as transactions become serialized and slow.
Architect for scaling writes.
Optimize for low write contention.
Optimize wide. Make writes as parallel as you can.

Sharded Counters

Handy conversion guide for back of envelope performance estimate:
2.5 million seconds per month
1 request per second = 2.5 million requests per month
40 requests per second = 100 million requests per month
400 requests per second = 1 billion requests per month