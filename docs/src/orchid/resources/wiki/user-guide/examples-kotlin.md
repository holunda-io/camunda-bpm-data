---
title: Kotlin Examples
---

## Kotlin Examples

The following snippets demonstrate the usage of the library from Kotlin

### Define variable

``` kotlin

import io.holunda.data.CamundaBpmDataKotlin

object Variables {
    val ORDER_ID = stringVariable("orderId")
    val ORDER: VariableFactory<Order> = customVariable("order")
    val ORDER_APPROVED = booleanVariable("orderApproved")
    val ORDER_POSITION: VariableFactory<OrderPosition> = customVariable("orderPosition")
    val ORDER_TOTAL: VariableFactory<BigDecimal> = customVariable("orderTotal")
}
```

### Read variable from Java delegate

``` kotlin
@Configuration
class JavaDelegates {

    @Bean
    fun calculateOrderPositions() = JavaDelegate { execution ->
        val orderPosition = ORDER_POSITION.from(execution).get()
        // order position is of type OrderPosition
    }
}
```

### Write variable from Java delegate

``` kotlin
import java.math.BigDecimal

@Configuration
class JavaDelegates {

    @Bean
    fun calculateOrderPositions() = JavaDelegate { execution ->
        val orderPosition = ORDER_POSITION.from(execution).get()
        ORDER_TOTAL.on(execution).set {
            orderPosition.netCost.times(BigDecimal.valueOf(orderPosition.amount))
        }
    }
}
```

### Remove variable from Java delegate

``` kotlin
@Configuration
class JavaDelegates {

    @Bean
    fun removeTotal() = JavaDelegate { execution ->
        ORDER_TOTAL.on(execution).remove()
    }
}
```

### Update variable from Java delegate

``` kotlin
import java.math.BigDecimal
@Configuration
class JavaDelegates {

    @Bean
    fun calculateOrderPositions() = JavaDelegate { execution ->
        val orderPosition = ORDER_POSITION.from(execution).get()
        ORDER_TOTAL.on(execution).update {
            it.plus(orderPosition.netCost.times(BigDecimal.valueOf(orderPosition.amount)))
        }
    }
}
```

### Fluent API to remove several variables

``` kotlin
import io.holunda.camunda.bpm.data.remove

@Configuration
class JavaDelegates {

    @Bean
    fun removeProcessVariables() = JavaDelegate { execution ->
        execution
            .remove(ORDER_ID)
            .remove(ORDER)
            .remove(ORDER_APPROVED)
            .remove(ORDER_TOTAL)
            .removeLocal(ORDER_POSITIONS)
    }
}
```

### Fluent API to set several variables

``` kotlin
@Component
class SomeService(
    private val runtimeService: RuntimeService,
    private val taskService: TaskService
) {

    fun setNewValuesForExecution(executionId: String, rderId: String, orderApproved: Boolean) {
        runtimeService.writer(executionId)
            .set(ORDER_ID, orderId)
            .set(ORDER_APPROVED, orderApproved)
            .update(ORDER_TOTAL, { amount -> amount.add(10) })
    }

    fun setNewValuesForTask(taskId: String, orderId: String, orderApproved: Boolean) {
        taskService.writer(taskId)
            .set(ORDER_ID, orderId)
            .set(ORDER_APPROVED, orderApproved)
    }

  fun start() {
      val variables = createProcessVariables()
          .set(ORDER_ID, "4711")
          .set(ORDER_APPROVED, false)
      runtimeService.startProcessInstanceById("myId", "businessKey", variables)
  }
}
```

### Fluent API to read several variables

``` kotlin
@Component
class SomeService(
  private val runtimeService: RuntimeService,
  private val taskService: TaskService
) {

  fun readValuesFromExecution(executionId: String): String {
      val reader = CamundaBpmData.reader(runtimeService, executionId)
      val orderId = reader.get(ORDER_ID)
      val orderApproved = reader.get(ORDER_APPROVED)
      if (orderApproved) {
          // ...
      }
      return orderId
  }

  fun readValuesFromTask(taskId: String ): String {
      val reader = CamundaBpmData.reader(taskService, taskId)
      val orderId = reader.get(ORDER_ID)
      val orderApproved = reader.get(ORDER_APPROVED)
      if (orderApproved) {
          // ...
      }
      return orderId
  }
}
```

### Anti-Corruption-Layer: Wrap variables to correlate

``` kotlin
@Component
class SomeService {

  val MESSAGE_ACL = CamundaBpmDataMapper.identityReplace("__transient", true);

  fun correlate() {
      val variables = CamundaBpmData.builder()
          .set(ORDER_ID, "4711")
          .set(ORDER_APPROVED, false)
          .build();
      runtimeService.correlateMessage("message_1", MESSAGE_ACL.wrap(variables));
  }
}
```

### Anti-Corruption-Layer: Check and wrap variables to correlate

``` kotlin
@Component
class SomeService {

    val MY_ACL = CamundaBpmDataACL.guardTransformingReplace(
        "__transient",
        true,
        VariablesGuard(exists(ORDER_ID)),
        IdentityVariableMapTransformer
    );

  fun correlate() {
      val variables = CamundaBpmData.builder()
          .set(ORDER_ID, "4711")
          .set(ORDER_APPROVED, false)
          .build();
      runtimeService.correlateMessage("message_1", MESSAGE_ACL.checkAndWrap(variables));
  }
}
```

### Define Guards to validate variables in the process

``` kotlin
@Configuration
class VariableGuardConfiguration {

    companion object {
        const val LOAD_OPERATIONAL_FILE_GUARD = "loadOperationalFileGuard";
    }

    @Bean(LOAD_OPERATIONAL_FILE_GUARD)
    fun loadOperationalFileGuard(): ExecutionListener =
        DefaultGuardExecutionListener(
            listOf(
                REQUIRED_VALUE.exists(),
                FUTURE_VALUE.notExists(),
                THE_ANSWER.hasValue(42),
                MY_DIRECTION.hasOneOfValues(setOf("left", "up", "down")),
                USER_EMAIL.isEmail(),
                DOCUMENT_ID.isUuid(),
                DOCUMENT_BODY.matches { return@matches true },
                DOCUMENT_BODY.matches(this::validationMessageSupplier) { return@matches true },
                DOCUMENT_BODY.matchesRegexLocal(Regex("^Dude.*"), "Starts with 'Dude'")
            ), true
        )
        
    private fun validationMessageSupplier(variableFactory: VariableFactory<String>, localLabel: String, option: Optional<String>) =
        "Expecting$localLabel variable '${variableFactory.name}'  to allways match my document body matcher, but its value '${option.get()}' has not."
}
```

### Example project

For more examples, please check-out the Kotlin Example project, at
[Github](https://github.com/holunda-io/camunda-bpm-data/tree/develop/example/example-kotlin.
