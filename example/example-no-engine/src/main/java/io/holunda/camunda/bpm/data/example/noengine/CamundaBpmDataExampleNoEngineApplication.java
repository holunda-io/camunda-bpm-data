package io.holunda.camunda.bpm.data.example.noengine;

import io.holunda.camunda.bpm.data.CamundaBpmData;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CamundaBpmDataExampleNoEngineApplication implements ApplicationRunner {

  public static final VariableFactory<String> ORDER_ID = CamundaBpmData.stringVariable("orderId");
  public static final VariableFactory<Order> ORDER = CamundaBpmData.customVariable("order", Order.class);

  @Autowired
  private VariableManipulatingService component;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    component.workWithVariables();
  }

  public static void main(String[] args) {
    SpringApplication.run(CamundaBpmDataExampleNoEngineApplication.class, args);
  }
}
