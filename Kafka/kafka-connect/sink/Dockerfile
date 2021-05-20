FROM confluentinc/cp-kafka-connect-base

# Used for connector status query
USER root
RUN yum install -y jq findutils

USER appuser
ENV CONNECT_PLUGIN_PATH="/usr/share/java,/usr/share/confluent-hub-components"
RUN  confluent-hub install --no-prompt mongodb/kafka-connect-mongodb:1.5.0

COPY ./sink-conf.json /home/appuser/
COPY auth.properties /home/appuser/
