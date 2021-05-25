-- Simple table creation to test headless mode.
CREATE STREAM messages_stream (user_id BIGINT KEY, message VARCHAR)
WITH (KAFKA_TOPIC = 'hello_topic_json', VALUE_FORMAT='JSON');

CREATE TABLE messages AS
    SELECT user_id, count(*) as msg_count
    FROM messages_stream
    GROUP BY user_id
EMIT CHANGES;

-- See results:
-- $ kafkacat -b localhost:29092 -C -u  -t _confluent-ksql-ksql_docker_headlessquery_CTAS_MESSAGES_0-Aggregate-Aggregate-Materialize-changelog
-- {"USER_ID":1,"ROWTIME":1621961172199,"KSQL_AGG_VARIABLE_0":2}
-- {"USER_ID":2,"ROWTIME":1621961172200,"KSQL_AGG_VARIABLE_0":1}
