package io.holunda.camunda.bpm.data.example.process;

import java.util.function.Supplier;
import org.camunda.bpm.engine.runtime.ProcessInstance;

/** Order Approval process instance supplier. */
public class OrderApprovalInstance implements Supplier<ProcessInstance> {

  /** Underlying instance. */
  private final ProcessInstance instance;

  /**
   * Creates the supplier.
   *
   * @param instance instance.
   */
  public OrderApprovalInstance(ProcessInstance instance) {
    this.instance = instance;
  }

  /**
   * Retrieval of the instance.
   *
   * @return instance.
   */
  public ProcessInstance get() {
    return instance;
  }
}
