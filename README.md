## realtime-streaming

The project exemplifies real-time data streaming into Snowflake, employing two distinct methodologies:

- Kafka Connect,
- Snowpark,

utilizing the Finnhub WebSocket stream as the real-time data source.

### Snowflake prerequisites
You have to create separate database:
~~~
CREATE DATABASE financial_data;
~~~
with two tables, one for data uploaded by Snowpark and one by Kafka Connect, respectively:
~~~
CREATE OR REPLACE TABLE FINANCIAL_DATA.PUBLIC.TRADE (
  PRICE FLOAT,
  SYMBOL VARCHAR(255),
  TIMESTAMP TIMESTAMP_NTZ(9),
  VOLUME FLOAT
);
~~~
~~~
CREATE OR REPLACE TABLE FINANCIAL_DATA.PUBLIC.TRADE_AS_JSON (
  RECORD_METADATA VARIANT,
  RECORD_CONTENT VARIANT
);
~~~
Run [script](scripts/generate_keys.sh) that generates key pair:
~~~
./generate_keys.sh
~~~
and copy the content of [snowflake_rsa_key.pub](scripts%2Fsnowflake_rsa_key.pub) file to following query:
~~~
ALTER USER YOUR_USERNAME SET RSA_PUBLIC_KEY='PUT YOUR PUBLIC KEY HERE';
~~~
and execute in Snowflake worksheet. At the end create warehouse:
~~~
CREATE WAREHOUSE compute_wh;
~~~

### Finnhub prerequisites
You have to acquire your personal API token at https://finnhub.io/.

### Run
First, build appropriate Docker images by executing build [script](scripts/build.sh):
~~~
./build.sh <ACCOUNT> <USERNAME> <FINNHUB_TOKEN>
~~~
and then in same directory run:
~~~
./run.sh
~~~

#### Links
- https://docs.snowflake.com/en/user-guide/data-load-snowpipe-streaming-kafka 
- https://docs.snowflake.com/en/user-guide/kafka-connector-install#label-kafka-connector-configuration-file 
- https://docs.confluent.io/platform/current/connect/userguide.html 
- https://stackoverflow.com/questions/51335621/kafka-connect-cluster-setup-or-launching-connect-workers 
- https://stackoverflow.com/questions/61521437/how-to-run-kafka-connect-connect-distributed-sh-in-the-background-daemon 
- https://stackoverflow.com/questions/29405727/java-lang-noclassdeffounderror-org-bouncycastle-jce-provider-bouncycastleprovid 
- https://www.youtube.com/watch?v=18gDPSOH3wU
