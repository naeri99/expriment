#!/bin/bash

#create network bridge if there is no kafka network
docker network create --driver bridge kafka

docker run -d --cpus="2" --memory="3g" --name kafkaone -p 9092:9092 --network kafka -e NODE_ID="1" -e CONNECTOR="1@kafkaone:9093" -e IP_NAME="INTERNAL://kafkaone:9092" -e SECURITYP_MAP="listener.security.protocol.map=INTERNAL:PLAINTEXT,CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT" -e INNER_CON="inter.broker.listener.name=INTERNAL" -e LISTENER="INTERNAL://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093" bumory1987/kafkaman:mversion2

docker run -d --cpus="2" --memory="3g" --name kafkatwo -p 9093:9092 --network kafka -e NODE_ID="2" -e CONNECTOR="1@kafkaone:9093,2@kafkaone:9093" -e IP_NAME="INTERNAL://kafkatwo:9092" -e SECURITYP_MAP="listener.security.protocol.map=INTERNAL:PLAINTEXT,CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT" -e INNER_CON="inter.broker.listener.name=INTERNAL" -e LISTENER="INTERNAL://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093" bumory1987/kafkaman:mversion2

docker run -d --cpus="2" --memory="3g" --name kafkathree -p 9094:9092 --network kafka -e NODE_ID="3" -e CONNECTOR="1@kafkaone:9093,2@kafkatwo:9093,3@kafkathree:9093" -e IP_NAME="INTERNAL://kafkathree:9092" -e SECURITYP_MAP="listener.security.protocol.map=INTERNAL:PLAINTEXT,CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT" -e INNER_CON="inter.broker.listener.name=INTERNAL" -e LISTENER="INTERNAL://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093" bumory1987/kafkaman:mversion2

#2 storage
docker run -it --privileged --network kafka -d --name storage bumory1987/storage:spstorage

#3 spark_worker
docker run -d --network kafka --cpus="2" --memory="4g" --name master --privileged -p 8083:8083 -p 7077:7077 -e "SPARK_MASTER_HOST=master" -e "STORAGE=storage" bumory1987/sparks:masterv2

#4 spark_worker
docker run -d --cpus="2" --memory="3g" --network kafka --name worker_0 --privileged -p 8090:8090 -e "SPARK_MASTER_HOST=master" -e "SPARK_MASTER_PORT=7077" -e "SPARK_MASTER_WEBUI_PORT=8083" -e "SPARK_WORKER_WEBUI_PORT=8090" -e "STORAGE=storage" bumory1987/sparks:workerv2

docker run -d --cpus="2" --memory="3g" --network kafka --name worker_1 --privileged -p 8091:8090 -e "SPARK_MASTER_HOST=master" -e "SPARK_MASTER_PORT=7077" -e "SPARK_MASTER_WEBUI_PORT=8083" -e "SPARK_WORKER_WEBUI_PORT=8090" -e "STORAGE=storage" bumory1987/sparks:workerv2

docker run -d --cpus="2" --memory="3g" --network kafka --name worker_2 --privileged -p 8092:8090 -e "SPARK_MASTER_HOST=master" -e "SPARK_MASTER_PORT=7077" -e "SPARK_MASTER_WEBUI_PORT=8083" -e "SPARK_WORKER_WEBUI_PORT=8090" -e "STORAGE=storage" bumory1987/sparks:workerv2

docker run --cpus="3" --memory="3g" --name=backend_1 --network=kafka -d bumory1987/backend:0.07

docker run -d --network kafka --privileged --name controller -p 8889:8889 -e "STORAGE=storage" bumory1987/sparks:ui

