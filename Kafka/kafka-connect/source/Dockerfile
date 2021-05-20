FROM confluentinc/cp-kafka-connect-base

# Used for connector status query
USER root
RUN yum install -y jq findutils

USER appuser
ENV CONNECT_PLUGIN_PATH="/usr/share/java,/usr/share/confluent-hub-components"
RUN  confluent-hub install --no-prompt jcustenborder/kafka-connect-spooldir:2.0.62

RUN mkdir -p /home/appuser/data/source
RUN mkdir -p /home/appuser/data/finished
RUN mkdir -p /home/appuser/data/error

COPY ./data/*.csv /home/appuser/data/source/
COPY ./source-conf.json /home/appuser/
