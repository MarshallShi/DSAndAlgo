Docker basic command:

docker run 
Run a image as a container, by default will download the image as well if not found.

docker build
Build a docker image with a Dockerfile as default input.

docker ps
List all the running docker container

docker pull
Pull a specific image from docker hub.

docker commit
Save the container as a image.

docker exec 
Execute a command in docker container.

CMD vs ENTRYPOINT
Networking
Storage

Compose
A configuration file in yml, to define multiple services: web, database, messaging...
==but link will be deprecated soon, with docker swarm
docker-compose up, use --link in 'docker run' to link services, such as app with database.

Sampel CALMS E2E docker-compose.yml 

oracle-db-cms-dfs:
  container_name: ${USERNAME}_oracle-db-cms-dfs
  domainname: domain.whiteclarkegroup.com
  hostname: db12c-cms-dfs
  image: "${DOCKER_REGISTRY_GROUP_URL}/oracle-db-se-cms-dfs:${DOCKER_IMAGE_TAG}"
  volumes:
    - ${USERNAME}_oracle-db-cms-dfs_imp_exp_dir:/u05/app/oracle/oradata/ORCL/DP_DIR/
    - ${USERNAME}_oracle-db-cms-dfs_db_scripts:/opt/WCG/Calms2CMS-db/
  ports:
    - "${DOCKER_DNS_IP}:1522:1521"
    - "${DOCKER_DNS_IP}:5501:5500"
  privileged: true
  shm_size: 2g
  restart: unless-stopped
  tty: true

oracle-db-fmo-dfs:
  container_name: ${USERNAME}_oracle-db-fmo-dfs
  domainname: domain.whiteclarkegroup.com
  hostname: db12c-fmo-dfs
  image: "${DOCKER_REGISTRY_GROUP_URL}/oracle-db-pe-fmo-dfs:${DOCKER_IMAGE_TAG}"
  volumes:
    - ${USERNAME}_oracle-db-fmo-dfs_imp_exp_dir:/u05/app/oracle/oradata/ORCL/DP_DIR/
    - ${USERNAME}_oracle-db-fmo-dfs_db_scripts:/opt/WCG/DFSProductWebApp-db/
  ports:
    - "${DOCKER_DNS_IP}:1521:1521"
    - "${DOCKER_DNS_IP}:5500:5500"
  privileged: true
  shm_size: 2g
  restart: unless-stopped
  tty: true

ibm-was-mocks-dfs:
  container_name: ${USERNAME}_ibm-was-mocks-dfs
  domainname: domain.whiteclarkegroup.com
  hostname: ibm-was-mocks-dfs
  image: "${DOCKER_REGISTRY_GROUP_URL}/ibm-was-mocks-dfs:${DOCKER_IMAGE_TAG}"
  volumes:
    - ${USERNAME}_ibm-was-mocks-dfs_was_logs:/opt/IBM/WebSphere/AppServer/profiles/was_profile/logs/server1/
  ports:
    - "${DOCKER_DNS_IP}:9082:9080"
    - "${DOCKER_DNS_IP}:9062:9060"
    - "${DOCKER_DNS_IP}:9045:9043"
    - "${DOCKER_DNS_IP}:7278:7276"
    - "${DOCKER_DNS_IP}:7779:7777"
    - "${DOCKER_DNS_IP}:8882:8880"
  environment:
     - PROFILE=${COMPOSE_PROFILE}
  privileged: true
  restart: unless-stopped
  tty: true


ibm-was-cms-dfs:
  container_name: ${USERNAME}_ibm-was-cms-dfs
  domainname: domain.whiteclarkegroup.com
  hostname: ibm-was-cms-dfs
  image: "${DOCKER_REGISTRY_GROUP_URL}/ibm-was-cms-dfs:${DOCKER_IMAGE_TAG}"
  volumes:
    - ${USERNAME}_ibm-was-cms-dfs_was_logs:/opt/IBM/WebSphere/AppServer/profiles/was_profile/logs/server1/
    - ${USERNAME}_ibm-was-cms-dfs_wcg_logs:/opt/WCG/Calms2CMS-app/logs/
    - ${USERNAME}_ibm-was-cms-dfs_wcg_config:/opt/WCG/Calms2CMS-app/calms2cms/
  links:
    - "oracle-db-cms-dfs:db12c-cms-dfs.domain.whiteclarkegroup.com"
    - "ibm-was-mocks-dfs:ibm-was-mocks-dfs.domain.whiteclarkegroup.com"
  ports:
    - "${DOCKER_DNS_IP}:9081:9080"
    - "${DOCKER_DNS_IP}:9061:9060"
    - "${DOCKER_DNS_IP}:9044:9043"
    - "${DOCKER_DNS_IP}:7277:7276"
    - "${DOCKER_DNS_IP}:7778:7777"
    - "${DOCKER_DNS_IP}:8881:8880"
  environment:
     - CM_URL=${COMPOSE_CM_URL}
     - FMO_URL=${COMPOSE_FMO_URL}
     - CMSTOPMS_URL=${COMPOSE_FMO_URL}/cxf/CMSToPMSService
     - EDM_WEB_URL=http://ibm-was-mocks-dfs:9080/ODMMock
     - BPSEARCH_URL=http://ibm-was-mocks-dfs:9080/BPSearch
     - PROFILE=${COMPOSE_PROFILE}
  privileged: true
  restart: unless-stopped
  tty: true

ibm-was-fmo-dfs:
  container_name: ${USERNAME}_ibm-was-fmo-dfs
  domainname: domain.whiteclarkegroup.com
  hostname: ibm-was-fmo-dfs
  image: "${DOCKER_REGISTRY_GROUP_URL}/ibm-was-fmo-dfs:${DOCKER_IMAGE_TAG}"
  volumes:
    - ${USERNAME}_ibm-was-fmo-dfs_was_logs:/opt/IBM/WebSphere/AppServer/profiles/was_profile/logs/server1/
    - ${USERNAME}_ibm-was-fmo-dfs_wcg_logs:/opt/WCG/DFSProductWebApp-app/logs/
    - ${USERNAME}_ibm-was-fmo-dfs_wcg_config:/opt/WCG/DFSProductWebApp-app/config/
    - ${USERNAME}_ibm-was-fmo-dfs_wcg_catalyst:/opt/WCG/DFSProductWebApp-app/catalyst/
  links:
    - "oracle-db-fmo-dfs:db12c-fmo-dfs.domain.whiteclarkegroup.com"
    - "ibm-was-mocks-dfs:ibm-was-mocks-dfs.domain.whiteclarkegroup.com"
  ports:
    - "${DOCKER_DNS_IP}:9080:9080"
    - "${DOCKER_DNS_IP}:9060:9060"
    - "${DOCKER_DNS_IP}:9043:9043"
    - "${DOCKER_DNS_IP}:7276:7276"
    - "${DOCKER_DNS_IP}:7777:7777"
    - "${DOCKER_DNS_IP}:8880:8880"
  environment:
     - CMS_URL=${COMPOSE_CMS_URL}
     - FMO_URL=http://ibm-was-fmo-dfs:9080/WebApp
     - EDM_WEB_URL=http://ibm-was-mocks-dfs:9080/ODMMock
     - BPSEARCH_URL=http://ibm-was-mocks-dfs:9080/BPSearch
     - BPSERVICE_URL=http://ibm-was-mocks-dfs:9080/BPService
     - NOTIFICATION_URL=http://ibm-was-mocks-dfs:9080/NotificationMock
     - CDSDECISIONREQUEST_URL=http://ibm-was-mocks-dfs:9080/CDSDecisionRequest
     - CDS_URL=http://ibm-was-mocks-dfs:9080/CDSMock
     - PROFILE=${COMPOSE_PROFILE}
  privileged: true
  restart: unless-stopped
  tty: true

apache-httpd-mocks-dfs:
  container_name: ${USERNAME}_apache-httpd-mocks-dfs
  domainname: domain.whiteclarkegroup.com
  hostname: apache-httpd-mocks-dfs
  image: "${DOCKER_REGISTRY_GROUP_URL}/apache-httpd-mocks-dfs:${DOCKER_IMAGE_TAG}"
  volumes_from:
    - "ibm-was-mocks-dfs"
  links:
    - "ibm-was-mocks-dfs:ibm-was-mocks-dfs.domain.whiteclarkegroup.com"
  ports:
    - "${DOCKER_DNS_IP}:82:80"
  privileged: true
  restart: unless-stopped
  tty: true

apache-httpd-cms-dfs:
  container_name: ${USERNAME}_apache-httpd-cms-dfs
  domainname: domain.whiteclarkegroup.com
  hostname: apache-httpd-cms-dfs
  image: "${DOCKER_REGISTRY_GROUP_URL}/apache-httpd-cms-dfs:${DOCKER_IMAGE_TAG}"
  volumes_from:
    - "oracle-db-cms-dfs"
    - "ibm-was-cms-dfs"
  links:
    - "ibm-was-cms-dfs:ibm-was-cms-dfs.domain.whiteclarkegroup.com"
    - "oracle-db-cms-dfs:db12c-cms-dfs.domain.whiteclarkegroup.com"
  ports:
    - "${DOCKER_DNS_IP}:81:80"
  privileged: true
  restart: unless-stopped
  tty: true

apache-httpd-fmo-dfs:
  container_name: ${USERNAME}_apache-httpd-fmo-dfs
  domainname: domain.whiteclarkegroup.com
  hostname: apache-httpd-fmo-dfs
  image: "${DOCKER_REGISTRY_GROUP_URL}/apache-httpd-fmo-dfs:${DOCKER_IMAGE_TAG}"
  volumes_from:
    - "oracle-db-fmo-dfs"
    - "ibm-was-fmo-dfs"
  links:
    - "ibm-was-fmo-dfs:ibm-was-fmo-dfs.domain.whiteclarkegroup.com"
    - "oracle-db-fmo-dfs:db12c-fmo-dfs.domain.whiteclarkegroup.com"
  ports:
    - "${DOCKER_DNS_IP}:80:80"
  privileged: true
  restart: unless-stopped
  tty: true

Registry
Engine

Docker layered architecture.


Docker swarm

docker swarm init  -> swarm manager node.
docker swarm join  -> worker.

On manager node:
docker service create --replicas=3 my-web-server


Kubernetes

kubectl run --replicas=1000 my-web-server

API Server, etcd, kubelet, Containter runtime, controller, scheduler.


