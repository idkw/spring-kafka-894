package com.example.idkw.issue894;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ExampleService {


    private final KafkaTemplate<String, Object> template;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ObjectMapper mapper = new ObjectMapper();
    private final Random random = new Random();

    @Autowired
    public ExampleService(KafkaTemplate<String, Object> template) {
        this.template = template;
    }

    public void sendRandom() {
        ProducerDto dto = new ProducerDto();
        dto.setName(String.valueOf(random.nextLong()));
        sendEvent(dto);
        log.info("Sent event {} of class {}", asString(dto), dto.getClass().getName());
    }

    public void sendEvent(Object object) {
        template.send("example", object);
    }

    @KafkaListener(topics = "example")
    public void onConsumerExample(ConsumerDto dto) {
        log.info("Received event {} of class {}", asString(dto), dto.getClass().getName());
    }

    private String asString(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
