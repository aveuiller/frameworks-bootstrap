#!/bin/bash

# Based on https://kafka.apache.org/quickstart
# Depends on 0_start_server.sh
# Depends on 1_create_topic.sh

KAFKA_HOME="${HOME}/Software/kafka_2.13-2.8.0"

# Read event
"${KAFKA_HOME}"/bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092