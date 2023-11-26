package com.mohey.food.ordering.service.kafka.producer.service.impl;

import com.mohey.food.ordering.service.kafka.producer.exception.KafkaProducerException;
import com.mohey.food.ordering.service.kafka.producer.service.KafkaProducer;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.function.BiConsumer;

@Slf4j
@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {
    private final KafkaTemplate<K, V> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, K key, V message, BiConsumer<SendResult<K, V>, Throwable> callback) {
        log.info("Sending message={} to topic={}", message, topicName);
        try {
            this.kafkaTemplate.send(topicName, message).whenCompleteAsync(callback);
        } catch (KafkaException ex) {
            log.error("Error on kafka producer with key: {}, message: {}, and exception: {}",
                    key, message, ex.getMessage());

            throw new KafkaProducerException("Error on kafka producer with " +
                    "key: " + key + ", message: " + message);
        }
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            this.kafkaTemplate.destroy();
        }

    }
}
