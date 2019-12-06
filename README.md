# ActiveMQ to Kafka Bridge

This is a bridge exposing a "minimal" Apache ActiveMQ broker (with http and tcp connectors), accepting messages on a specific queue
and "dispatching" messages to a Apache Kafka cluster.

## Build

To build the bridge, just use Maven:

```
$ mvn clean install
```

## Docker

Once you have built the bridge, you can directly create a Docker image using the provided `Dockerfile`:

```
$ docker build -t mybridge .
```

## Environment Variables

You can configure the bridge at startup using environment variables. Here's the complete list of variables:

| Variable                       | Description                                                 | Default Value    |
|--------------------------------|-------------------------------------------------------------|------------------|
| `LOGGING_LEVEL`                | Log level for all loggers                                   | `info`           |
| `HTTP_PORT`                    | ActiveMQ HTTP connector port number (bridge input)          | `8080`           |
| `TCP_PORT`                     | ActiveMQ TCP connector port number (bridge input)           | `61616`          |
| `JMS_QUEUE`                    | ActiveMQ JMS queue name                                     | `INCOMING`       |
| `KAFKA_ENTRYPOINT`             | Kafka broker location                                       | `localhost:9092` |
| `KAFKA_TOPIC`                  | Name of the Kafka topic where to produce messages           | `INBOUND`        |
| `JETTY_CONFIG`                 | Optional path to a `jetty.xml` configuration for ActiveMQ   |                  |
| `SYNCOPE_ACCESS`               | URL of the Apache Syncope backend used for authentication   |                  |
| `ACTIVEMQ_USERNAME`            | "Bot" user to consume from bridged ActiveMQ broker          |                  |
| `ACTIVEMQ_PASSWORD`            | "Bot" user password to consume from bridged ActiveMQ broker |                  |
| `ACTIVEMQ_HTTP_MAX_FRAME_SIZE` | Max frame size used for ActiveMQ HTTP connector             | `10000000`       |
| `ACTIVEMQ_TCP_MAX_FRAME_SIZE`  | Max frame size used for ActiveMQ TCP connector              | `10000000`       |