package io.holunda.camunda.bpm.data.delegate;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.delegate.DelegateCaseExecution;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.camunda.bpm.model.cmmn.CmmnModelInstance;
import org.camunda.bpm.model.cmmn.instance.CmmnElement;

public class VariableAwareDelegateCaseExecution extends AbstractVariableAwareDelegate implements DelegateCaseExecution {

  private final DelegateCaseExecution execution;

  public VariableAwareDelegateCaseExecution(DelegateCaseExecution execution) {
    super(execution);
    this.execution = execution;
  }

  @Override
  public String getId() {
    return execution.getId();
  }

  @Override
  public String getCaseInstanceId() {
    return execution.getCaseInstanceId();
  }

  @Override
  public String getEventName() {
    return execution.getEventName();
  }

  @Override
  public String getCaseBusinessKey() {
    return execution.getCaseBusinessKey();
  }

  @Override
  public String getCaseDefinitionId() {
    return execution.getCaseDefinitionId();
  }

  @Override
  public String getParentId() {
    return execution.getParentId();
  }

  @Override
  public String getActivityId() {
    return execution.getActivityId();
  }

  @Override
  public String getActivityName() {
    return execution.getActivityName();
  }

  @Override
  public String getTenantId() {
    return execution.getTenantId();
  }

  @Override
  public boolean isAvailable() {
    return execution.isAvailable();
  }

  @Override
  public boolean isEnabled() {
    return execution.isEnabled();
  }

  @Override
  public boolean isDisabled() {
    return execution.isDisabled();
  }

  @Override
  public boolean isActive() {
    return execution.isActive();
  }

  @Override
  public boolean isSuspended() {
    return execution.isSuspended();
  }

  @Override
  public boolean isTerminated() {
    return execution.isTerminated();
  }

  @Override
  public boolean isCompleted() {
    return execution.isCompleted();
  }

  @Override
  public boolean isFailed() {
    return execution.isFailed();
  }

  @Override
  public boolean isClosed() {
    return execution.isClosed();
  }

  @Override
  public String getBusinessKey() {
    return execution.getBusinessKey();
  }

  @Override
  public String getVariableScopeKey() {
    return execution.getVariableScopeKey();
  }

  @Override
  public Map<String, Object> getVariables() {
    return execution.getVariables();
  }

  @Override
  public VariableMap getVariablesTyped() {
    return execution.getVariablesTyped();
  }

  @Override
  public VariableMap getVariablesTyped(boolean deserializeValues) {
    return execution.getVariablesTyped(deserializeValues);
  }

  @Override
  public Map<String, Object> getVariablesLocal() {
    return execution.getVariablesLocal();
  }

  @Override
  public VariableMap getVariablesLocalTyped() {
    return execution.getVariablesLocalTyped();
  }

  @Override
  public VariableMap getVariablesLocalTyped(boolean deserializeValues) {
    return execution.getVariablesLocalTyped(deserializeValues);
  }

  @Override
  public Object getVariable(String variableName) {
    return execution.getVariable(variableName);
  }

  @Override
  public Object getVariableLocal(String variableName) {
    return execution.getVariableLocal(variableName);
  }

  @Override
  public <T extends TypedValue> T getVariableTyped(
    String variableName) {
    return execution.getVariableTyped(variableName);
  }

  @Override
  public <T extends TypedValue> T getVariableTyped(
    String variableName, boolean deserializeValue) {
    return execution.getVariableTyped(variableName, deserializeValue);
  }

  @Override
  public <T extends TypedValue> T getVariableLocalTyped(
    String variableName) {
    return execution.getVariableLocalTyped(variableName);
  }

  @Override
  public <T extends TypedValue> T getVariableLocalTyped(
    String variableName, boolean deserializeValue) {
    return execution.getVariableLocalTyped(variableName, deserializeValue);
  }

  @Override
  public Set<String> getVariableNames() {
    return execution.getVariableNames();
  }

  @Override
  public Set<String> getVariableNamesLocal() {
    return execution.getVariableNamesLocal();
  }

  @Override
  public void setVariable(String variableName, Object value) {
    execution.setVariable(variableName, value);
  }

  @Override
  public void setVariableLocal(String variableName, Object value) {
    execution.setVariableLocal(variableName, value);
  }

  @Override
  public void setVariables(Map<String, ?> variables) {
    execution.setVariables(variables);
  }

  @Override
  public void setVariablesLocal(Map<String, ?> variables) {
    execution.setVariablesLocal(variables);
  }

  @Override
  public boolean hasVariables() {
    return execution.hasVariables();
  }

  @Override
  public boolean hasVariablesLocal() {
    return execution.hasVariablesLocal();
  }

  @Override
  public boolean hasVariable(String variableName) {
    return execution.hasVariable(variableName);
  }

  @Override
  public boolean hasVariableLocal(String variableName) {
    return execution.hasVariableLocal(variableName);
  }

  @Override
  public void removeVariable(String variableName) {
    execution.removeVariable(variableName);
  }

  @Override
  public void removeVariableLocal(String variableName) {
    execution.removeVariableLocal(variableName);
  }

  @Override
  public void removeVariables(Collection<String> variableNames) {
    execution.removeVariables(variableNames);
  }

  @Override
  public void removeVariablesLocal(Collection<String> variableNames) {
    execution.removeVariablesLocal(variableNames);
  }

  @Override
  public void removeVariables() {
    execution.removeVariables();
  }

  @Override
  public void removeVariablesLocal() {
    execution.removeVariablesLocal();
  }

  @Override
  public ProcessEngineServices getProcessEngineServices() {
    return execution.getProcessEngineServices();
  }

  @Override
  public ProcessEngine getProcessEngine() {
    return execution.getProcessEngine();
  }

  @Override
  public CmmnModelInstance getCmmnModelInstance() {
    return execution.getCmmnModelInstance();
  }

  @Override
  public CmmnElement getCmmnModelElementInstance() {
    return execution.getCmmnModelElementInstance();
  }
}
