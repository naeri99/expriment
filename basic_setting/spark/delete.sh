#!/bin/bash

docker stop kafkathree

docker stop kafkatwo

docker stop kafkaone


docker stop master

docker stop worker_0

docker stop worker_1

docker stop worker_2

docker stop backend_1

docker stop storage

docker stop controller

docker rm kafkathree

docker rm kafkatwo

docker rm kafkaone

docker rm master

docker rm worker_0

docker rm worker_1

docker rm worker_2

docker rm backend_1

docker rm storage

docker rm controller



