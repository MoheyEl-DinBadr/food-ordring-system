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
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.io.Serializable;

@Slf4j
@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {
    private KafkaTemplate<K, V> kafkaTemplate;

    @Override
    public void send(String topicName, K key, V message, ListenableFutureCallback<SendResult<K, V>> callback) {
        log.info("Sending message={} to topic={}", message, topicName);
        try {
            this.kafkaTemplate.send(topicName, message).addCallback(callback);
        }catch (KafkaException ex){
            log.error("Error on kafka producer with key: {}, message: {}, and exception: {}",
                    key, message, ex.getMessage());
            callback.onFailure(
                    new KafkaProducerException("Error on kafka producer with " +
                            "key: "+ key + ", message: " + message));
        }
    }

    @PreDestroy
    public void close(){
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            this.kafkaTemplate.destroy();
        }

    }
}
