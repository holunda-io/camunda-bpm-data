package io.holunda.camunda.bpm.data.example.process;

import org.camunda.bpm.engine.runtime.ProcessInstance;

public class OrderApprovalInstance {

  private final ProcessInstance instance;

  public OrderApprovalInstance(ProcessInstance instance) {
    this.instance = instance;
  }

  public ProcessInstance getInstance() {
    return instance;
  }
}
