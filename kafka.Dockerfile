FROM confluentinc/cp-kafka:6.0.14

ARG ACCOUNT
ARG USERNAME
ARG PRIVATE_KEY
USER root

# download Snowflake's Kafka connector
WORKDIR /usr/share/java/kafka
RUN wget https://repo1.maven.org/maven2/com/snowflake/snowflake-kafka-connector/2.1.2/snowflake-kafka-connector-2.1.2.jar

# initialize Snowflake Kafka connector properties
WORKDIR /etc/kafka/
RUN echo "name: realtime-streaming" >> SF_connect.properties
RUN echo "connector.class: com.snowflake.kafka.connector.SnowflakeSinkConnector" >> SF_connect.properties
RUN echo "tasks.max: 1" >> SF_connect.properties
RUN echo "topics: trade" >> SF_connect.properties
RUN echo "snowflake.topic2table.map: trade:trade_as_json" >> SF_connect.properties
RUN echo "buffer.count.records: 10000" >> SF_connect.properties
RUN echo "buffer.flush.time: 60" >> SF_connect.properties
RUN echo "buffer.size.bytes: 5000000" >> SF_connect.properties
RUN echo "snowflake.url.name: $ACCOUNT.snowflakecomputing.com:443" >> SF_connect.properties
RUN echo "snowflake.user.name: $USERNAME" >> SF_connect.properties
RUN echo "$PRIVATE_KEY" | tr -d '\r\n' > tmp_key.pem
RUN echo "snowflake.private.key: $(cat tmp_key.pem)" >> SF_connect.properties
#RUN echo "snowflake.private.key: $PRIVATE_KEY" >> SF_connect.properties
RUN echo "snowflake.database.name: financial_data" >> SF_connect.properties
RUN echo "snowflake.schema.name: public" >> SF_connect.properties
RUN echo "key.converter: org.apache.kafka.connect.storage.StringConverter" >> SF_connect.properties
RUN echo "value.converter: com.snowflake.kafka.connector.records.SnowflakeJsonConverter" >> SF_connect.properties

# download libraries that are not distributed with kafka connector
WORKDIR /usr/share/java/kafka
RUN wget "https://search.maven.org/classic/remotecontent?filepath=org/bouncycastle/bcpkix-fips/1.0.3/bcpkix-fips-1.0.3.jar"
RUN wget "https://search.maven.org/classic/remotecontent?filepath=org/bouncycastle/bc-fips/1.0.1/bc-fips-1.0.1.jar"

ENTRYPOINT [ "/bin/sh", "-c", "/etc/confluent/docker/run"]




