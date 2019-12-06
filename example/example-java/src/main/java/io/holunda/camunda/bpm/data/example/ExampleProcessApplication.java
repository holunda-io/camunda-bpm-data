package io.holunda.camunda.bpm.data.example;

import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.variable.VariableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

import static io.holunda.camunda.bpm.data.example.ApprovalProcessConstants.*;

@SpringBootApplication
public class ExampleProcessApplication {

  private static final Logger LOG = LoggerFactory.getLogger(ExampleProcessApplication.class);

  public static void main(String[] args) {
    SpringApplication.run(ExampleProcessApplication.class, args);
  }

  @Bean
  public JavaDelegate loadDataDelegate() {
    return delegateExecution -> {


      APPROVAL_ID.on(delegateExecution).set(UUID.randomUUID().toString());
      APPROVAL_DATE.on(delegateExecution).set(Date.from(Instant.now()));
      APPROVAL_QUANTITY.on(delegateExecution).set(Integer.MAX_VALUE);
      APPROVAL_QUANTITY_LONG.on(delegateExecution).set(Long.MAX_VALUE);
      APPROVAL_QUANTITY_SHORT.on(delegateExecution).set(Short.MAX_VALUE);
      APPROVAL_AMOUNT.on(delegateExecution).set(Double.MAX_VALUE-0.000001);
      APPROVAL_CRITICAL.on(delegateExecution).set(Boolean.TRUE);
    };
  }

  @Bean
  public JavaDelegate readDataDelegate() {
    return delegateExecution -> {
      LOG.info("{}", APPROVAL_ID.from(delegateExecution).get());
      LOG.info("{}", APPROVAL_DATE.from(delegateExecution).get());
      LOG.info("{}", APPROVAL_QUANTITY.from(delegateExecution).get());
      LOG.info("{}", APPROVAL_QUANTITY_LONG.from(delegateExecution).get());
      LOG.info("{}", APPROVAL_QUANTITY_SHORT.from(delegateExecution).get());
      LOG.info("{}", APPROVAL_AMOUNT.from(delegateExecution).get());
      LOG.info("{}", APPROVAL_CRITICAL.from(delegateExecution).get());

      final VariableMap variablesTyped = delegateExecution.getVariablesTyped();

      LOG.info("{}", APPROVAL_ID.from(variablesTyped).get());
    };
  }

}
