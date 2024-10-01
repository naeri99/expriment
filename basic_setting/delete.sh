#!/bin/bash

docker stop kafkathree

docker stop kafkatwo

docker stop kafkaone

docker stop flink_1

docker stop flink_2

docker stop flink_3

docker stop flink_4

# docker stop backend_1

docker rm kafkathree

docker rm kafkatwo

docker rm kafkaone

docker rm flink_1

docker rm flink_2

docker rm flink_3

docker rm flink_4

docker stop backend_1





