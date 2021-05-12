#!/bin/bash

# Based on https://kafka.apache.org/quickstart

KAFKA_HOME="${HOME}/Software/kafka_2.13-2.8.0"

# Start the ZooKeeper service
# Note: Soon, ZooKeeper will no longer be required by Apache Kafka.
"${KAFKA_HOME}"/bin/zookeeper-server-start.sh "${KAFKA_HOME}/config/zookeeper.properties" &

# Start the Kafka broker service
"${KAFKA_HOME}"/bin/kafka-server-start.sh "${KAFKA_HOME}/config/server.properties"
