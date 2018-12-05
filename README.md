# Context

Example project to reproduce the issue describe [here](https://github.com/spring-projects/spring-kafka/issues/894)

# How to reproduce
1. Start a kafka broker by the way you like or with the included `kafka/docker-compose.yml` file 
by running `docker-compose up --build -d`, you'l also need to add `127.0.0.1   kafka` to your hosts file
2. Open your IDE (tested with Intellij)
3. Create a Spring Boot Launch Configuration on the `com.example.idkw.issue894.Issue894Application` class
4. Run the launch configuration
5. Send an HTTP POST request on http://localhost:8080/example  
This will trigger the sending of a `ProducerDto` event into Kafka  
The @KafkaListener in `ExampleService` will then induce the consumption of this event
6. Everything works fine and you should see the logs :
```
Sent event {} of class {}
Received event {} of class {}
```

Now, in the `pom.xml` file, uncomment the `spring-boot-devtools` dependency and reproduce the steps from step 4.
You should get the following error :

```
2018-12-05 20:25:53.181 ERROR 1235 --- [ntainer#0-0-C-1] o.s.kafka.listener.LoggingErrorHandler   : Error while processing: ConsumerRecord(topic = example, partition = 0, offset = 0, CreateTime = 1544037945634, serialized key size = -1, serialized value size = 30, headers = RecordHeaders(headers = [], isReadOnly = false), key = null, value = com.example.idkw.issue894.ProducerDto@77313cdf)

org.springframework.kafka.listener.ListenerExecutionFailedException: Listener method could not be invoked with the incoming message
Endpoint handler details:
Method [public void com.example.idkw.issue894.ExampleService.onConsumerExample(com.example.idkw.issue894.ConsumerDto)]
Bean [com.example.idkw.issue894.ExampleService@25a84167]; nested exception is org.springframework.messaging.converter.MessageConversionException: Cannot handle message; nested exception is org.springframework.messaging.converter.MessageConversionException: Cannot convert from [com.example.idkw.issue894.ProducerDto] to [com.example.idkw.issue894.ConsumerDto] for GenericMessage [payload=com.example.idkw.issue894.ProducerDto@77313cdf, headers={kafka_offset=0, kafka_consumer=org.apache.kafka.clients.consumer.KafkaConsumer@5cede6f9, kafka_timestampType=CREATE_TIME, kafka_receivedMessageKey=null, kafka_receivedPartitionId=0, kafka_receivedTopic=example, kafka_receivedTimestamp=1544037945634}], failedMessage=GenericMessage [payload=com.example.idkw.issue894.ProducerDto@77313cdf, headers={kafka_offset=0, kafka_consumer=org.apache.kafka.clients.consumer.KafkaConsumer@5cede6f9, kafka_timestampType=CREATE_TIME, kafka_receivedMessageKey=null, kafka_receivedPartitionId=0, kafka_receivedTopic=example, kafka_receivedTimestamp=1544037945634}]
	at org.springframework.kafka.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:292) ~[spring-kafka-2.2.2.RELEASE.jar:2.2.2.RELEASE]
	at org.springframework.kafka.listener.adapter.RecordMessagingMessageListenerAdapter.onMessage(RecordMessagingMessageListenerAdapter.java:79) ~[spring-kafka-2.2.2.RELEASE.jar:2.2.2.RELEASE]
	at org.springframework.kafka.listener.adapter.RecordMessagingMessageListenerAdapter.onMessage(RecordMessagingMessageListenerAdapter.java:50) ~[spring-kafka-2.2.2.RELEASE.jar:2.2.2.RELEASE]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeOnMessage(KafkaMessageListenerContainer.java:1207) [spring-kafka-2.2.2.RELEASE.jar:2.2.2.RELEASE]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeOnMessage(KafkaMessageListenerContainer.java:1200) [spring-kafka-2.2.2.RELEASE.jar:2.2.2.RELEASE]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeRecordListener(KafkaMessageListenerContainer.java:1161) [spring-kafka-2.2.2.RELEASE.jar:2.2.2.RELEASE]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.doInvokeWithRecords(KafkaMessageListenerContainer.java:1142) [spring-kafka-2.2.2.RELEASE.jar:2.2.2.RELEASE]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeRecordListener(KafkaMessageListenerContainer.java:1083) [spring-kafka-2.2.2.RELEASE.jar:2.2.2.RELEASE]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.invokeListener(KafkaMessageListenerContainer.java:911) [spring-kafka-2.2.2.RELEASE.jar:2.2.2.RELEASE]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.pollAndInvoke(KafkaMessageListenerContainer.java:727) [spring-kafka-2.2.2.RELEASE.jar:2.2.2.RELEASE]
	at org.springframework.kafka.listener.KafkaMessageListenerContainer$ListenerConsumer.run(KafkaMessageListenerContainer.java:676) [spring-kafka-2.2.2.RELEASE.jar:2.2.2.RELEASE]
	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511) [na:1.8.0_191]
	at java.util.concurrent.FutureTask.run$$$capture(FutureTask.java:266) [na:1.8.0_191]
	at java.util.concurrent.FutureTask.run(FutureTask.java) [na:1.8.0_191]
	at java.lang.Thread.run(Thread.java:748) [na:1.8.0_191]
Caused by: org.springframework.messaging.converter.MessageConversionException: Cannot handle message; nested exception is org.springframework.messaging.converter.MessageConversionException: Cannot convert from [com.example.idkw.issue894.ProducerDto] to [com.example.idkw.issue894.ConsumerDto] for GenericMessage [payload=com.example.idkw.issue894.ProducerDto@77313cdf, headers={kafka_offset=0, kafka_consumer=org.apache.kafka.clients.consumer.KafkaConsumer@5cede6f9, kafka_timestampType=CREATE_TIME, kafka_receivedMessageKey=null, kafka_receivedPartitionId=0, kafka_receivedTopic=example, kafka_receivedTimestamp=1544037945634}], failedMessage=GenericMessage [payload=com.example.idkw.issue894.ProducerDto@77313cdf, headers={kafka_offset=0, kafka_consumer=org.apache.kafka.clients.consumer.KafkaConsumer@5cede6f9, kafka_timestampType=CREATE_TIME, kafka_receivedMessageKey=null, kafka_receivedPartitionId=0, kafka_receivedTopic=example, kafka_receivedTimestamp=1544037945634}]
	... 15 common frames omitted
Caused by: org.springframework.messaging.converter.MessageConversionException: Cannot convert from [com.example.idkw.issue894.ProducerDto] to [com.example.idkw.issue894.ConsumerDto] for GenericMessage [payload=com.example.idkw.issue894.ProducerDto@77313cdf, headers={kafka_offset=0, kafka_consumer=org.apache.kafka.clients.consumer.KafkaConsumer@5cede6f9, kafka_timestampType=CREATE_TIME, kafka_receivedMessageKey=null, kafka_receivedPartitionId=0, kafka_receivedTopic=example, kafka_receivedTimestamp=1544037945634}]
	at org.springframework.messaging.handler.annotation.support.PayloadArgumentResolver.resolveArgument(PayloadArgumentResolver.java:144) ~[spring-messaging-5.1.3.RELEASE.jar:5.1.3.RELEASE]
	at org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:117) ~[spring-messaging-5.1.3.RELEASE.jar:5.1.3.RELEASE]
	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:147) ~[spring-messaging-5.1.3.RELEASE.jar:5.1.3.RELEASE]
	at org.springframework.messaging.handler.invocation.InvocableHandlerMethod.invoke(InvocableHandlerMethod.java:116) ~[spring-messaging-5.1.3.RELEASE.jar:5.1.3.RELEASE]
	at org.springframework.kafka.listener.adapter.HandlerAdapter.invoke(HandlerAdapter.java:48) ~[spring-kafka-2.2.2.RELEASE.jar:2.2.2.RELEASE]
	at org.springframework.kafka.listener.adapter.MessagingMessageListenerAdapter.invokeHandler(MessagingMessageListenerAdapter.java:283) ~[spring-kafka-2.2.2.RELEASE.jar:2.2.2.RELEASE]
	... 14 common frames omitted

```

When the envent is sent into kafka, if you debug the class `org.springframework.kafka.support.converter.AbstractJavaTypeMapper` 
you should see that on line 142 the ` clazz` parameter is of class `com.example.idkw.issue894.ProducerDto` 
with class loader `RestartClassLoader` whereas what was stored in the `classIdMapping` map is the same class but
with the class loader `Launcher$AppClassLoader`

This class loader mismatch makes the mapper ignores the type mapping we've configured, and the ProducerDto event
is serialized and sent to kafka without a proper type id header. Thus on the consumer side, it is not converted to a ConsumerDto.

This issue occurs when using spring-boot-devtools which uses two class loaders to implement the hot restart feature.
The issue can be prevented by setting `System.setProperty("spring.devtools.restart.enabled", "false");` as mentionned 
in the [documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html) but this then prevents
the use of the dev tools restart feature.
