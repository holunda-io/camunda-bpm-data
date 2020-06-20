package io.holunda.camunda.bpm.data.mapping;

import static io.holunda.camunda.bpm.data.CamundaBpmData.customVariable;

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;

public class TransientVariableMappingListener implements ExecutionListener {

  private final VariableFactory<VariableMap> transientVariable;
  private final boolean local;

  public TransientVariableMappingListener(final String variableName, final boolean local) {
    this(customVariable(variableName, VariableMap.class), local);
  }

  public TransientVariableMappingListener(final VariableFactory<VariableMap> transientVariable, final boolean local) {
    this.transientVariable = transientVariable;
    this.local = local;
  }

  @Override
  public void notify(DelegateExecution execution) {
    final VariableMap variables = transientVariable.from(execution).get();

    if (local) {
      execution.setVariablesLocal(variables);
    } else {
      execution.setVariables(variables);
    }
  }

  public VariableMap wrap(VariableMap variables) {
    return createTypedTransientVariable(transientVariable.getName(), variables);
  }

  /**
   * Helper to create a Map containing transient variables hidden in the given map under the given key.
   *
   * @param processVariableKey the variable name to use for the additional variables
   * @param variables the variables to store
   * @return a newly created map containing the given variables as transient objectTypedValue
   */
  public static VariableMap createTypedTransientVariable(String processVariableKey, VariableMap variables) {
    return Variables.createVariables()
      .putValueTyped(processVariableKey, Variables.objectValue(
        variables, true)
        .serializationDataFormat(Variables.SerializationDataFormats.JAVA)
        .create()
      );
  }
}
