package io.holunda.camunda.bpm.data.example.noengine;

import io.holunda.camunda.bpm.data.reader.VariableReader;
import org.camunda.bpm.engine.variable.VariableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static io.holunda.camunda.bpm.data.CamundaBpmData.builder;
import static io.holunda.camunda.bpm.data.CamundaBpmData.reader;
import static io.holunda.camunda.bpm.data.example.noengine.CamundaBpmDataExampleNoEngineApplication.ORDER;
import static io.holunda.camunda.bpm.data.example.noengine.CamundaBpmDataExampleNoEngineApplication.ORDER_ID;
import static java.time.Instant.now;

@Component
public class VariableManipulatingService {

  private static final Logger logger = LoggerFactory.getLogger(VariableManipulatingService.class);

  public boolean workWithVariables() {

    final OrderPosition position = new OrderPosition("Pencil", BigDecimal.valueOf(10.00), 3L);
    List<OrderPosition> positions = Arrays.asList(position);


    VariableMap variables = builder()
      .set(ORDER_ID, "4711")
      .set(ORDER, new Order("4711", now(), positions))
      .build();

    String orderId = ORDER_ID.from(variables).get();

    VariableReader variableReader = reader(variables);
    Order order = variableReader.get(ORDER);

    logger.info("Successfully read order {}: {}", orderId, order);

    return true;
  }
}
