package io.holunda.camunda.bpm.data.delegate;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineServices;
import org.camunda.bpm.engine.delegate.DelegateCaseExecution;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.value.TypedValue;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.UserTask;

public class VariableAwareDelegateTask extends AbstractVariableAwareDelegate implements DelegateTask {

  private final DelegateTask task;

  public VariableAwareDelegateTask(DelegateTask task) {
    super(task);
    this.task = task;
  }

  @Override
  public String getId() {
    return task.getId();
  }

  @Override
  public String getName() {
    return task.getName();
  }

  @Override
  public void setName(String name) {
    task.setName(name);
  }

  @Override
  public String getDescription() {
    return task.getDescription();
  }

  @Override
  public void setDescription(String description) {
    task.setDescription(description);
  }

  @Override
  public int getPriority() {
    return task.getPriority();
  }

  @Override
  public void setPriority(int priority) {
    task.setPriority(priority);
  }

  @Override
  public String getProcessInstanceId() {
    return task.getProcessInstanceId();
  }

  @Override
  public String getExecutionId() {
    return task.getExecutionId();
  }

  @Override
  public String getProcessDefinitionId() {
    return task.getProcessDefinitionId();
  }

  @Override
  public String getCaseInstanceId() {
    return task.getCaseInstanceId();
  }

  @Override
  public String getCaseExecutionId() {
    return task.getCaseExecutionId();
  }

  @Override
  public String getCaseDefinitionId() {
    return task.getCaseDefinitionId();
  }

  @Override
  public Date getCreateTime() {
    return task.getCreateTime();
  }

  @Override
  public String getTaskDefinitionKey() {
    return task.getTaskDefinitionKey();
  }

  @Override
  public DelegateExecution getExecution() {
    return task.getExecution();
  }

  @Override
  public DelegateCaseExecution getCaseExecution() {
    return task.getCaseExecution();
  }

  @Override
  public String getEventName() {
    return task.getEventName();
  }

  @Override
  public void addCandidateUser(String userId) {
    task.addCandidateUser(userId);
  }

  @Override
  public void addCandidateUsers(Collection<String> candidateUsers) {
    task.addCandidateUsers(candidateUsers);
  }

  @Override
  public void addCandidateGroup(String groupId) {
    task.addCandidateGroup(groupId);
  }

  @Override
  public void addCandidateGroups(Collection<String> candidateGroups) {
    task.addCandidateGroups(candidateGroups);
  }

  @Override
  public String getOwner() {
    return task.getOwner();
  }

  @Override
  public void setOwner(String owner) {
    task.setOwner(owner);
  }

  @Override
  public String getAssignee() {
    return task.getAssignee();
  }

  @Override
  public void setAssignee(String assignee) {
    task.setAssignee(assignee);
  }

  @Override
  public Date getDueDate() {
    return task.getDueDate();
  }

  @Override
  public void setDueDate(Date dueDate) {
    task.setDueDate(dueDate);
  }

  @Override
  public String getDeleteReason() {
    return task.getDeleteReason();
  }

  @Override
  public void addUserIdentityLink(String userId, String identityLinkType) {
    task.addUserIdentityLink(userId, identityLinkType);
  }

  @Override
  public void addGroupIdentityLink(String groupId, String identityLinkType) {
    task.addGroupIdentityLink(groupId, identityLinkType);
  }

  @Override
  public void deleteCandidateUser(String userId) {
    task.deleteCandidateUser(userId);
  }

  @Override
  public void deleteCandidateGroup(String groupId) {
    task.deleteCandidateGroup(groupId);
  }

  @Override
  public void deleteUserIdentityLink(String userId, String identityLinkType) {
    task.deleteUserIdentityLink(userId, identityLinkType);
  }

  @Override
  public void deleteGroupIdentityLink(String groupId, String identityLinkType) {
    task.deleteGroupIdentityLink(groupId, identityLinkType);
  }

  @Override
  public Set<IdentityLink> getCandidates() {
    return task.getCandidates();
  }

  @Override
  public UserTask getBpmnModelElementInstance() {
    return task.getBpmnModelElementInstance();
  }

  @Override
  public String getTenantId() {
    return task.getTenantId();
  }

  @Override
  public Date getFollowUpDate() {
    return task.getFollowUpDate();
  }

  @Override
  public void setFollowUpDate(Date followUpDate) {
    task.setFollowUpDate(followUpDate);
  }

  @Override
  public void complete() {
    task.complete();
  }

  @Override
  public String getVariableScopeKey() {
    return task.getVariableScopeKey();
  }

  @Override
  public Map<String, Object> getVariables() {
    return task.getVariables();
  }

  @Override
  public VariableMap getVariablesTyped() {
    return task.getVariablesTyped();
  }

  @Override
  public VariableMap getVariablesTyped(boolean deserializeValues) {
    return task.getVariablesTyped(deserializeValues);
  }

  @Override
  public Map<String, Object> getVariablesLocal() {
    return task.getVariablesLocal();
  }

  @Override
  public VariableMap getVariablesLocalTyped() {
    return task.getVariablesLocalTyped();
  }

  @Override
  public VariableMap getVariablesLocalTyped(boolean deserializeValues) {
    return task.getVariablesLocalTyped(deserializeValues);
  }

  @Override
  public Object getVariable(String variableName) {
    return task.getVariable(variableName);
  }

  @Override
  public Object getVariableLocal(String variableName) {
    return task.getVariableLocal(variableName);
  }

  @Override
  public <T extends TypedValue> T getVariableTyped(
    String variableName) {
    return task.getVariableTyped(variableName);
  }

  @Override
  public <T extends TypedValue> T getVariableTyped(
    String variableName, boolean deserializeValue) {
    return task.getVariableTyped(variableName, deserializeValue);
  }

  @Override
  public <T extends TypedValue> T getVariableLocalTyped(
    String variableName) {
    return task.getVariableLocalTyped(variableName);
  }

  @Override
  public <T extends TypedValue> T getVariableLocalTyped(
    String variableName, boolean deserializeValue) {
    return task.getVariableLocalTyped(variableName, deserializeValue);
  }

  @Override
  public Set<String> getVariableNames() {
    return task.getVariableNames();
  }

  @Override
  public Set<String> getVariableNamesLocal() {
    return task.getVariableNamesLocal();
  }

  @Override
  public void setVariable(String variableName, Object value) {
    task.setVariable(variableName, value);
  }

  @Override
  public void setVariableLocal(String variableName, Object value) {
    task.setVariableLocal(variableName, value);
  }

  @Override
  public void setVariables(Map<String, ?> variables) {
    task.setVariables(variables);
  }

  @Override
  public void setVariablesLocal(Map<String, ?> variables) {
    task.setVariablesLocal(variables);
  }

  @Override
  public boolean hasVariables() {
    return task.hasVariables();
  }

  @Override
  public boolean hasVariablesLocal() {
    return task.hasVariablesLocal();
  }

  @Override
  public boolean hasVariable(String variableName) {
    return task.hasVariable(variableName);
  }

  @Override
  public boolean hasVariableLocal(String variableName) {
    return task.hasVariableLocal(variableName);
  }

  @Override
  public void removeVariable(String variableName) {
    task.removeVariable(variableName);
  }

  @Override
  public void removeVariableLocal(String variableName) {
    task.removeVariableLocal(variableName);
  }

  @Override
  public void removeVariables(Collection<String> variableNames) {
    task.removeVariables(variableNames);
  }

  @Override
  public void removeVariablesLocal(Collection<String> variableNames) {
    task.removeVariablesLocal(variableNames);
  }

  @Override
  public void removeVariables() {
    task.removeVariables();
  }

  @Override
  public void removeVariablesLocal() {
    task.removeVariablesLocal();
  }

  @Override
  public BpmnModelInstance getBpmnModelInstance() {
    return task.getBpmnModelInstance();
  }

  @Override
  public ProcessEngineServices getProcessEngineServices() {
    return task.getProcessEngineServices();
  }

  @Override
  public ProcessEngine getProcessEngine() {
    return task.getProcessEngine();
  }

}
