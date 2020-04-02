Why rate limit?
1. UX
2. Security
3. Operational cost
(DDOS)

Ways:
1. User based.
2. Concurrent
3. Location / IP : to make sure certain area's service are not affected.
4. Server (rare)


Design 1: Token bucket. (Redis)
Say 5 request per min maximum.
User 1, 11:01:00  5
User 2, 11:13:00  3

Some function to : fetch token and update token.
Each time a new request come, decrease the available times, if it is 0, then reject the request, otherwise 
decrease the available times.

Design 2: Leaky Bucket
Like a queue, but with a size limit, unprocessed request will be discarded.
Request is flatten

Design 3: Fixed window counter
Potential impact in edge case between minutes, due to the window size control nature.

Design 4: Sliding Logs.
Real time count, parse the log files, in formate : U1 : [ TimeStamp: xyz    ]
if rate limit is 10/min.
Filtering out the rows per U1, if more than 10 in the past min, then reject next.
-Drawback: too many memory consumption.

Design 5: Sliding window counter.
Use the counter instead all records.

However, in distributed environment, there are two issues.
1) Inconsistency, if two requests come in and routed to two different load balancer and RateLimit service 
instances, it will potentially breach the limit.
-- One approach is use sticky session, so request from same user should always go to the same load balancer.
Not recommended, as it is not balanced.
-- Other approach is to use lock.
It will introduce addtional latency.

No perfect solution, in the end the rate limit should be relaxed.



