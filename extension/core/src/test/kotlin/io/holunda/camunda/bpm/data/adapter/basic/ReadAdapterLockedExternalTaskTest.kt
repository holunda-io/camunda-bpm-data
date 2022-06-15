package io.holunda.camunda.bpm.data.adapter.basic

import io.holunda.camunda.bpm.data.CamundaBpmData
import org.assertj.core.api.Assertions.assertThat
import org.camunda.bpm.engine.externaltask.LockedExternalTask
import org.camunda.bpm.engine.variable.VariableMap
import org.junit.Test
import java.util.*

class ReadAdapterLockedExternalTaskTest {

  private val uuidVar = CamundaBpmData.uuidVariable("uuidVar")

  @Test
  fun `get UUID type from variableMap directly`() {
    val map = CamundaBpmData.builder()
      .set(uuidVar, UUID.randomUUID())
      .build()

    assertThat(uuidVar.from(map).get()).isInstanceOf(UUID::class.java)
  }

  @Test
  fun `get UUID type from ExternalTask`() {
    val variables = CamundaBpmData.builder()
      .set(uuidVar, UUID.randomUUID())
      .build()

    val task = LockedExternalTaskFake(
      id = UUID.randomUUID().toString(),
      variables = variables
    )

    assertThat(task.variables).isSameAs(variables)
    assertThat(uuidVar.from(task).get()).isInstanceOf(UUID::class.java)
  }
}

data class LockedExternalTaskFake(
  private var id: String? = null,
  private var topicName: String? = null,
  private var workerId: String? = null,
  private var lockExpirationTime: Date? = null,
  private var processInstanceId: String? = null,
  private var executionId: String? = null,
  private var activityId: String? = null,
  private var activityInstanceId: String? = null,
  private var processDefinitionId: String? = null,
  private var processDefinitionKey: String? = null,
  private var processDefinitionVersionTag: String? = null,
  private var retries: Int? = null,
  private var errorMessage: String? = null,
  private var errorDetails: String? = null,
  private var variables: VariableMap? = null,
  private var tenantId: String? = null,
  private var priority: Long = 0,
  private var businessKey: String? = null,
  private var extensionProperties: Map<String, String>? = null,
) : LockedExternalTask {
  override fun getId() = id

  override fun getTopicName() = topicName

  override fun getWorkerId() = workerId

  override fun getLockExpirationTime() = lockExpirationTime

  override fun getProcessInstanceId() = processInstanceId

  override fun getExecutionId() = executionId

  override fun getActivityId() = activityId

  override fun getActivityInstanceId() = activityInstanceId

  override fun getProcessDefinitionId() = processDefinitionId

  override fun getProcessDefinitionKey() = processDefinitionKey

  override fun getProcessDefinitionVersionTag() = processDefinitionVersionTag

  override fun getRetries() = retries

  override fun getErrorMessage() = errorMessage

  override fun getErrorDetails() = errorDetails

  override fun getVariables() = variables

  override fun getTenantId() = tenantId

  override fun getPriority() = priority

  override fun getBusinessKey() = businessKey

  override fun getExtensionProperties() = extensionProperties
}
