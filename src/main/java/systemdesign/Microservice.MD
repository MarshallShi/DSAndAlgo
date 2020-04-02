How to scale up a micro service?

1. Horizontal scaling.
Add more instances for the same service.

2. Further functional decomposation.
Split to more services and do separate deployment.

3. Data partition.
Usually the bottleneck is data access.
We can partition the data say by id, so service can talk to different parts of the data.
Can use API gateway to route the traffic.


API Gateway
We can set up different API Gateway for different source request. API Gateway Web, API Gateway Mobile, API Gateway 3rdparty.

Advantage:
1). Authentication. 
Simplify authentication, JWT token implementation before call all services.
2). Use HTTPS only in API Gateway could be possible.
3). Act as Load Balancer.
4). Insulation.
Also can help with:
1). Caching.
2). Managing access quotas.
3). API health monitoring.
4). API Versioning.
5). Chaos moneky testing.
6). A/B testing.
Disadvantage:
1). Additonal latency.
2). Complexity.



Service Registery and Discovery
How to register?
1). Self register, each service keep updating the register every x minutes for its address.
2). Service register asking each service, listen to cluster scale up and down events.
Discovery




