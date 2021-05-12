#!/bin/bash

# Based on https://kafka.apache.org/quickstart
# Depends on 0_start_server.sh

KAFKA_HOME="${HOME}/Software/kafka_2.13-2.8.0"


# Create topic
"${KAFKA_HOME}"/bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
# Describe topic
"${KAFKA_HOME}"/bin/kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:9092
