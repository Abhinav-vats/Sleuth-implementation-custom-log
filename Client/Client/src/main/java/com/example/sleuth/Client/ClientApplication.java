package com.example.sleuth.Client;

import brave.baggage.BaggageField;
import brave.baggage.BaggageFields;
import brave.baggage.CorrelationScopeConfig;
import brave.baggage.CorrelationScopeCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Bean
	public RestTemplate getRetRestTemplate(RestTemplateBuilder builder){
		return builder.setConnectTimeout(Duration.ofSeconds(3)).build();
	}

	// We want to control the way baggage fields are being set
	@Bean
	CorrelationScopeCustomizer mdcOverridingScopeCustomizer() {
		return builder -> {
			// We can't override [key] to flush on update so we need to remove the current entries
			builder.clear();
			// We're adding back the default ones related to tracing context
			builder.add(CorrelationScopeConfig.SingleCorrelationField.create(BaggageFields.TRACE_ID));
			builder.add(CorrelationScopeConfig.SingleCorrelationField.create(BaggageFields.SPAN_ID));
			builder.add(CorrelationScopeConfig.SingleCorrelationField.create(BaggageFields.SAMPLED));
			// We're setting the [key] baggage to flush on update
			builder.add(CorrelationScopeConfig.SingleCorrelationField
					.newBuilder(BaggageField.create("key"))
					.flushOnUpdate()
					.build());

		};
	}
}
