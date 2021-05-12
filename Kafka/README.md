# Kafka

Kafka is a versatile, distributed, streaming platform.

Resources: 

- https://kafka.apache.org/quickstart
- https://kafka.apache.org/documentation/
- Kafka administration UI: https://github.com/yahoo/CMAK

# Quickstart

This section showcases the general use of kafka from command line.

## Docker Compose

The file `docker-compose.yaml` defines a Kafka environment including one instance of:

- Zookeeper
- Kafka Broker
- KSQL DB Server

Note: The Kafka docker container will advertise his url as `kafka:9092` and `localhost:29092`,
to enable contact respectively from a docker container and the host.

## Write Topic

To create a new topic, you can do the following:
```shell
$ $KAFKA_HOME/bin/kafka-topics.sh --bootstrap-server localhost:29092 --create --topic ${topic_name}
Created topic ${topic_name}.
```

## Read Topic

To read the content of a topic from the beginning, you can use the following snippet:
```shell
$ $KAFKA_HOME/bin/kafka-topics.sh --bootstrap-server localhost:29092 --list
$ $KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server localhost:29092 --topic ${topic_name} --from-beginning

# Alternatively, use kafkacat. "-r" and "-s" options are used to deserialize Avro schema.
$ kafkacat -b localhost:29092 -r http://localhost:8081 -s avro -t ${topic_name}  -C -u | jq '.'
```

## KQSL

You can connect to the database using the following snippet:
```shell
$ docker exec -it kafka_ksql_1 /bin/bash
$ ksql

# Basic use of the database
ksql> list [topics|streams|tables]
ksql> print '<name>' from beginning;
```

# Kafka Raw integration

This project proposes a `Producer` and a `Consumer` respectively sending and polling `HelloMessages`.
The implementation is handling communication with both JSON and Avro (by default, JSON is used).

To run the project, you can use the following maven command:
```shell
# Serialize messages in Avro (requires a schema-registry)
$ mvn clean compile exec:java -Dexec.mainClass="io.github.aveuiller.learning.kafka.raw.Main" -Dexec.args="-t avro"
# Serialize messages in JSON (no requirement)
$ mvn clean compile exec:java -Dexec.mainClass="io.github.aveuiller.learning.kafka.raw.Main" -Dexec.args="-t json"
```

# Kafka Streams

The project kafka-streams contains the code from the [Official tutorial](https://kafka.apache.org/25/documentation/streams/tutorial).

The different mains can be started using:
```shell
# Ensure topic existence
$ $KAFKA_HOME/bin/kafka-topics.sh --bootstrap-server localhost:29092 --create --topic streams-plaintext-input
# Fill the topic
$ $KAFKA_HOME/bin/kafka-console-producer.sh --bootstrap-server localhost:29092 --topic streams-plaintext-input
>enter some input
>Potentially multiple messages
>^C%

# Start stream processes.
$ mvn clean compile exec:java -Dexec.mainClass="io.github.aveuiller.learning.kafka.streams.LineSplit"
$ mvn clean compile exec:java -Dexec.mainClass="io.github.aveuiller.learning.kafka.streams.Pipe"
$ mvn clean compile exec:java -Dexec.mainClass="io.github.aveuiller.learning.kafka.streams.WordCount"
```

# Kafka Connect

## Source

**TODO**

## Sink

**TODO**

Resources:

- https://www.confluent.io/hub/mmolimar/kafka-connect-fs
- https://www.confluent.io/hub/mongodb/kafka-connect-mongodb
- https://docs.mongodb.com/kafka-connector/current/
- https://hub.docker.com/r/confluentinc/cp-kafka-connect
