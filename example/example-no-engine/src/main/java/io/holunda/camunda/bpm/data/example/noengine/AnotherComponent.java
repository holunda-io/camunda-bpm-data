package io.holunda.camunda.bpm.data.example.noengine;

import org.camunda.bpm.engine.variable.Variables;

public class AnotherComponent {
  void foo() {
    var variables = Variables.createVariables();

    CamundaBpmDataExampleNoEngineApplicationKt.getORDER_ID().from(variables).get();
  }
}
