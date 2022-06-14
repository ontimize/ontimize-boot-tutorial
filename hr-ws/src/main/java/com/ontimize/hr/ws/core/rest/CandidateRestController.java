package com.ontimize.hr.ws.core.rest;

import com.ontimize.hr.api.core.service.ICandidateService;
import com.ontimize.hr.model.core.kafka.factory.OntimizeKafkaFactory;
import com.ontimize.hr.model.core.kafka.factory.consumer.OntimizeKafkaConsumer;
import com.ontimize.hr.model.core.kafka.factory.consumer.OntimizeKafkaConsumerAction;
import com.ontimize.hr.model.core.kafka.factory.producer.OntimizeKafkaProducer;
import com.ontimize.hr.model.core.kafka.factory.producer.OntimizeKafkaProducerAction;
import com.ontimize.jee.server.rest.ORestController;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/candidates")
@ComponentScan(basePackageClasses = {com.ontimize.hr.api.core.service.ICandidateService.class})
public class CandidateRestController extends ORestController<ICandidateService> {

    @Autowired
    private ICandidateService candidateService;
    @Autowired(required = false)
    private OntimizeKafkaFactory ontimizeKafkaFactory;

    @Override
    public ICandidateService getService() {
        return this.candidateService;
    }

    @RequestMapping(value = "/testSendKafka", method = RequestMethod.GET)
    public ResponseEntity<String> testSendKafka() {
        OntimizeKafkaProducer<Integer, String> producer = this.ontimizeKafkaFactory.getProducer("candidate-productor");
        producer.sendMessage(1, String.valueOf(ThreadLocalRandom.current().nextInt(1,101)), new OntimizeKafkaProducerAction<Integer, String>() {
            @Override
            public void runOnSuccess(ProducerRecord<Integer, String> data) {
                System.out.println("envío exitoso");
            }

            @Override
            public void runOnFailure(ProducerRecord<Integer, String> data, Throwable throwable) {
                System.out.println("envío fallido");
            }
        });

        return new ResponseEntity<>("Sended!", HttpStatus.OK);
    }

    @RequestMapping(value = "/testReceiveKafka", method = RequestMethod.GET)
    public ResponseEntity<String> testReceiveKafka() {
        OntimizeKafkaConsumer<Integer, String> consumer = this.ontimizeKafkaFactory.getConsumer("offer-consumer");
        consumer.start(new OntimizeKafkaConsumerAction<Integer, String>() {
            @Override
            public void run(ConsumerRecord<Integer, String> record) {
                System.out.println("Mensaje: key " + record.key() + " value: " + record.value());
            }
        });

        return new ResponseEntity<>("It Works!", HttpStatus.OK);
    }
}