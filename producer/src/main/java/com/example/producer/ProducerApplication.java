package com.example.producer;

import org.reactivestreams.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

@SpringBootApplication
public class ProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

}

@RestController
class ReservationController {

	final IntervalMessageProducer imp;

	public ReservationController(IntervalMessageProducer imp) {
		this.imp = imp;
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value = "/sse/{n}")
	Publisher<GreetingResponse> stringPublisher(@PathVariable String n) {
		return this.imp.produceGreetings(new GreetingRequest(n));
	}
}

@Component
class IntervalMessageProducer {
	Flux<GreetingResponse> produceGreetings(GreetingRequest name) {
		return Flux.fromStream(Stream.generate(() -> "Hello " + name + " @ " + Instant.now())).map(el -> new GreetingResponse(el)).delayElements(Duration.ofSeconds(1));
	}
}

record GreetingRequest(String name) {};
record GreetingResponse(String greeting) {};


