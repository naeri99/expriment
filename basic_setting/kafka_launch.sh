#!/bin/bash

#create network bridge if there is no kafka network
docker network create --driver bridge kafka

docker run -d --cpus="2" --memory="3g" --name kafkaone -p 9092:9092 --network kafka -e NODE_ID="1" -e CONNECTOR="1@kafkaone:9093" -e IP_NAME="INTERNAL://kafkaone:9092" -e SECURITYP_MAP="listener.security.protocol.map=INTERNAL:PLAINTEXT,CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT" -e INNER_CON="inter.broker.listener.name=INTERNAL" -e LISTENER="INTERNAL://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093" bumory1987/kafkaman:mversion2

docker run -d --cpus="2" --memory="3g" --name kafkatwo -p 9093:9092 --network kafka -e NODE_ID="2" -e CONNECTOR="1@kafkaone:9093,2@kafkaone:9093" -e IP_NAME="INTERNAL://kafkatwo:9092" -e SECURITYP_MAP="listener.security.protocol.map=INTERNAL:PLAINTEXT,CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT" -e INNER_CON="inter.broker.listener.name=INTERNAL" -e LISTENER="INTERNAL://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093" bumory1987/kafkaman:mversion2

docker run -d --cpus="2" --memory="3g" --name kafkathree -p 9094:9092 --network kafka -e NODE_ID="3" -e CONNECTOR="1@kafkaone:9093,2@kafkatwo:9093,3@kafkathree:9093" -e IP_NAME="INTERNAL://kafkathree:9092" -e SECURITYP_MAP="listener.security.protocol.map=INTERNAL:PLAINTEXT,CONTROLLER:PLAINTEXT,EXTERNAL:PLAINTEXT" -e INNER_CON="inter.broker.listener.name=INTERNAL" -e LISTENER="INTERNAL://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093" bumory1987/kafkaman:mversion2


docker run --cpus="6" --memory="6g" --name=flink_1 -p 8081:8081 --network=kafka -e MASTER=flink_1 -e WORKER=flink_1 -d bumory1987/flink:masterv3

docker run --cpus="3" --memory="3g" --name=flink_2 -p 8082:8081 --network=kafka -e MASTER=flink_1 -e WORKER=flink_2 -e RPC_PORT=flink_1 -d bumory1987/flink:workerv3

docker run --cpus="3" --memory="3g" --name=backend_1 --network=kafka -d bumory1987/backend:0.05