High level architectural

One Master node, up to thousands of worker node.
Managing the whole cluster.
Configuration, failure detection, replication, scaling etc.

Worker Node
Each worker node is composed by multiple Pod.
Each Pod could be composed by multiple different containers.

Master -> Worker Node

Worker Node -> Pods -> Containers


kubectl run --replicas=1000 my-web-server

API Server, etcd, kubelet, Containter runtime, controller, scheduler.


Master: API Server, Scheduler, Controller Manager, ETCD.
API Server -- Respond to CLI or UI command.
Scheduler -- check nodes.
Controller Manager
ETCD -- key value pair DB to store the cluster information.

Worker: Kubelet, Kube-proxy, Pod, Containers.


Pods: basic scheduling unit.




