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
- Kafka Schema Registry
- An interactive ksqlDB Server
- An headless ksqlDB Server (you need to run the project `kafka-raw` to create the json topic it depends on)

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

This project defines a global [docker-compose.yaml](./kafka-connect/docker-compose.yaml) creating:
- A Kafka Broker (exposed on localhost:39092).
- A Zookeeper.
- A schema registry (exposed on localhost:28081).
- A MongoDB (exposed on localhost:27017).
- A MongoDB admin interface (exposed on localhost:8000)
- A Kafka to MongoDB sink connector.
- A CSV to Kafka source connector.

For a detailed explanation of the creation process, you can take a look at the tutorial
[Running a self-managed Kafka Connect worker for Confluent Cloud - *Robin Moffatt*](https://rmoff.net/2021/01/11/running-a-self-managed-kafka-connect-worker-for-confluent-cloud/).

## Usage

To run the process, you simply have to run the docker compose using `docker compose up -d --build`.

You can check the status of both source and sink connectors through the logs:
```shell
# Check the source
$ docker logs -f kafka-connect_connect-source_1                                                                                                  255 ↵
Waiting for Kafka Connect to start listening on connect-source:28082 ⏳
Fri May 21 09:52:27 UTC 2021  Kafka Connect listener HTTP state:  000  (waiting for 200)
# [...]
Fri May 21 09:52:57 UTC 2021  Kafka Connect listener HTTP state:  200  (waiting for 200)

--
+> Creating Kafka Connect source connectors
# [...]
HTTP/1.1 201 Created
# [...]

--
+> Checking connectors status
mongo-source  |  RUNNING  |  RUNNING

# Check the sink 
$ docker logs -f kafka-connect_connect-sink_1                                                                                                  255 ↵
Waiting for Kafka Connect to start listening on connect-source:38082 ⏳
Fri May 21 09:58:54 UTC 2021  Kafka Connect listener HTTP state:  000  (waiting for 200)
# [...]
Fri May 21 09:59:24 UTC 2021  Kafka Connect listener HTTP state:  200  (waiting for 200)

--
+> Creating Kafka Connect source connectors
# [...]
HTTP/1.1 201 Created
# [...]

--
+> Checking connectors status
mongo-sink  |  RUNNING  |  RUNNING

# If you need to debug on either the source or the sink
$ docker exec -it kafka-connect_connect-sink_1 /bin/bash
$ cat /tmp/connect.log 
```

If everything goes well, you should see the equivalent of our input CSV on the Mongo Express interface:
http://localhost:8000/db/kafka_connect/sink 

**Note: on MacOS, you may need to allocate more RAM to docker than the default 2Go (_e.g._ 6Go)**

## Source Configuration

We are mainly following along with the CSV part of the tutorial
[KSQL in Action: Enriching CSV Events with Data from RDBMS into AWS - *Robin Moffatt*](https://www.confluent.io/blog/ksql-in-action-enriching-csv-events-with-data-from-rdbms-into-AWS/).

For the sake of simplicity, we will embed the data in the docker image directly,
but a more suitable system would either be to contact an external file system or to mount a data volume on the container.

In order to transform the data correctly, we will generate a schema from the CSV file.
To do so, the connector we use provide a tool. We will have to build the repository to use it:
```shell
$ git clone //github.com/jcustenborder/kafka-connect-spooldir.git
$ cd kafka-connect-spooldir
# You may have to add the confluent repository in pom.xml to fetch the avro-serializer
#    <repositories>
#         <repository>
#            <id>confluent</id>
#            <name>Confluent</name>
#            <url>https://packages.confluent.io/maven/</url>
#        </repository>
#    </repositories>
$ mvn clean package

# This will generate Schema Generator classes among other things
$ find . -name "*SchemaGenerator.class*"
./target/classes/com/github/jcustenborder/kafka/connect/spooldir/CsvSchemaGenerator.class
./target/classes/com/github/jcustenborder/kafka/connect/spooldir/AbstractSchemaGenerator.class
./target/classes/com/github/jcustenborder/kafka/connect/spooldir/JsonSchemaGenerator.class 
```

Once those classes are available, you can generate the data schema from the directory [kafka-connect/source/data](./kafka-connect/source/data).

```shell
$ cd kafka-connect/source/data
$ mkdir -p /tmp/finished /tmp/source /tmp/error

# Add the previous classes to your classpath
$ export CLASSPATH="$(find $SPOOL_GIT_PATH/target/kafka-connect-target/usr/share/kafka-connect/kafka-connect-spooldir/ -type f -name '*.jar' | tr '\n' ':')"

# Run the generator, that will provide you an autogenerated schema
$ $KAFKA_HOME/kafka-run-class.sh com.github.jcustenborder.kafka.connect.spooldir.CsvSchemaGenerator \
  -t csv -f ./data_01.csv -c ./spool_conf.tmp -i id
#Configuration was dynamically generated. Please verify before submitting.
#Thu May 20 14:32:53 CEST 2021
key.schema={"name"\:"com.github.jcustenborder.kafka.connect.model.Key","type"\:"STRUCT","isOptional"\:false,"fieldSchemas"\:{"id"\:{"type"\:"STRING","isOptional"\:true}}}
csv.first.row.as.header=true
finished.path=/tmp/finished
input.path=/tmp/source
value.schema={"name"\:"com.github.jcustenborder.kafka.connect.model.Value","type"\:"STRUCT","isOptional"\:false,"fieldSchemas"\:{"id"\:{"type"\:"STRING","isOptional"\:true}," name"\:{"type"\:"STRING","isOptional"\:true}," creation_date"\:{"type"\:"STRING","isOptional"\:true},"\\tadmin"\:{"type"\:"STRING","isOptional"\:true}}}
```

The values above can then be adapted and injected in the `environment` in the file [docker-compose.yaml](./kafka-connect/docker-compose.yaml).

Once the pipeline runs, you can find the data in the broker:
````shell
$ kafkacat -b localhost:39092 -r http://localhost:28081 -C -u -s avro -t mongo-source
{"id": {"string": "2"}, "name": {"string": "john"}, "creation_date": {"string": "2019-01-01"}, "admin": {"string": "true"}}
{"id": {"string": "1"}, "name": {"string": "jack"}, "creation_date": {"string": "2020-01-01"}, "admin": {"string": "false"}}
````

You can also easily add new data files to the running pipeline using the following snippet:
```shell
$ docker cp source/data/data_01.csv kafka-connect_connect-source_1:/home/appuser/data/source/data_02.csv
```

## Sink Configuration

The sink is using the [mongo-kafka connector](https://github.com/mongodb/mongo-kafka),
available on [confluent hub](https://www.confluent.io/hub/mmolimar/kafka-connect-fs).

In order to avoid setting plaintext authentication in the configuration [sink-conf.json](./kafka-connect/sink/sink-conf.json),
we use a [FileConfigProvider](https://docs.confluent.io/platform/current/connect/security.html#externalizing-secrets)
to handle secrets management. 
As a result the secrets are imported from [auth.properties](./kafka-connect/sink/auth.properties).

All other configurations on this sink are pretty straightforward.
