package io.holunda.camunda.bpm.data.delegate;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.runtime.Incident;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowElement;

public class VariableAwareDelegateExecution extends AbstractVariableAwareDelegate implements DelegateExecution {

  private final DelegateExecution execution;

  public VariableAwareDelegateExecution(DelegateExecution execution) {
    super(execution);
    this.execution = execution;
  }

  @Override
  public String getProcessInstanceId() {
    return execution.getProcessInstanceId();
  }

  @Override
  public String getProcessBusinessKey() {
    return execution.getProcessBusinessKey();
  }

  @Override
  public void setProcessBusinessKey(String businessKey) {
    execution.setProcessBusinessKey(businessKey);
  }

  @Override
  public String getProcessDefinitionId() {
    return execution.getProcessDefinitionId();
  }

  @Override
  public String getParentId() {
    return execution.getParentId();
  }

  @Override
  public String getCurrentActivityId() {
    return execution.getCurrentActivityId();
  }

  @Override
  public String getCurrentActivityName() {
    return execution.getCurrentActivityName();
  }

  @Override
  public String getActivityInstanceId() {
    return execution.getActivityInstanceId();
  }

  @Override
  public String getParentActivityInstanceId() {
    return execution.getParentActivityInstanceId();
  }

  @Override
  public String getCurrentTransitionId() {
    return execution.getCurrentTransitionId();
  }

  @Override
  public DelegateExecution getProcessInstance() {
    return execution.getProcessInstance();
  }

  @Override
  public DelegateExecution getSuperExecution() {
    return execution.getSuperExecution();
  }

  @Override
  public boolean isCanceled() {
    return execution.isCanceled();
  }

  @Override
  public String getTenantId() {
    return execution.getTenantId();
  }

  @Override
  public void setVariable(String variableName, Object value, String activityId) {
    execution.setVariable(variableName, value, activityId);
  }

  @Override
  public Incident createIncident(String incidentType,
    String configuration) {
    return execution.createIncident(incidentType, configuration);
  }

  @Override
  public Incident createIncident(String incidentType,
    String configuration, String message) {
    return execution.createIncident(incidentType, configuration, message);
  }

  @Override
  public void resolveIncident(String incidentId) {
    execution.resolveIncident(incidentId);
  }

  @Override
  public String getId() {
    return execution.getId();
  }

  @Override
  public String getEventName() {
    return execution.getEventName();
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
  public BpmnModelInstance getBpmnModelInstance() {
    return execution.getBpmnModelInstance();
  }

  @Override
  public FlowElement getBpmnModelElementInstance() {
    return execution.getBpmnModelElementInstance();
  }

  @Override
  public ProcessEngineServices getProcessEngineServices() {
    return execution.getProcessEngineServices();
  }

  @Override
  public ProcessEngine getProcessEngine() {
    return execution.getProcessEngine();
  }
}
