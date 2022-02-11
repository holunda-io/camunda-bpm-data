## Add dependency

Current version available in Sonatype OSS Maven Central is:

In Apache Maven add to your `pom.xml`:

```xml
<dependency>
  <groupId>io.holunda.data</groupId>
  <artifactId>camunda-bpm-data</artifactId>
  <version>${camunda-bpm-data.version}</version>
</dependency>
```

For Gradle Kotlin DSL add to your `build.gradle.kts`:

```kotlin
implementation("io.holunda.data:camunda-bpm-data:${camunda-bpm-data.version}")
```

For Gradle Groovy DSL add to your `build.gradle`:

```groovy
implementation 'io.holunda.data:camunda-bpm-data:${camunda-bpm-data.version}'
```

## Declare process variable factories

First you have to define your process variables, by providing the variable name and type. For providing the type,
different convenience methods exist:

Here is an example in Java:

```java

import io.holunda.camunda.bpm.data.factory.VariableFactory;
import static io.holunda.camunda.bpm.data.CamundaBpmData.*;

public class OrderApproval {
  public static final VariableFactory<String> ORDER_ID = stringVariable("orderId");
  public static final VariableFactory<Order> ORDER = customVariable("order", Order.class);
  public static final VariableFactory<Boolean> ORDER_APPROVED = booleanVariable("orderApproved");
  public static final VariableFactory<OrderPosition> ORDER_POSITION = customVariable("orderPosition", OrderPosition.class);
  public static final VariableFactory<BigDecimal> ORDER_TOTAL = customVariable("orderTotal", BigDecimal.class);
}
```

## Access process variables from Java Delegate

If you want to access the process variable, call methods on the `ProcessVariableFactory` to configure the usage context,
and then invoke the variable access methods.

Here is an example, how it looks like to access variable from `JavaDelegate` implemented in Java. In this example,
the total amount is calculated from the amounts of order positions and stored in the process variable.

```java

@Configuration
class JavaDelegates {

  @Bean
  public JavaDelegate calculateOrderPositions() {
    return execution -> {
      OrderPosition orderPosition = ORDER_POSITION.from(execution).get();
      BigDecimal oldTotal = ORDER_TOTAL.from(execution).getOptional().orElse(BigDecimal.ZERO);
      BigDecimal newTotal = oldTotal.add(orderPosition.getNetCost().multiply(BigDecimal.valueOf(orderPosition.getAmount())));
      ORDER_TOTAL.on(execution).setLocal(newTotal);
    };
  }
}
```

## Variable access from REST Controller

Now imagine you are implementing a REST controller for a user task form which
loads data from the process application, displays it, captures some input and
sends that back to the process application to complete the user task. By doing so,
you will usually need to access process variables. Here is an example:


```java

@RestController
@RequestMapping("/task/approve-order")
public class ApproveOrderTaskController {

    private final TaskService taskService;

    public ApproveOrderTaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApproveTaskDto> loadTask(@PathVariable("taskId") String taskId) {
        Order order = ORDER.from(taskService, taskId).get();
        return ResponseEntity.ok(new ApproveTaskDto(order));
    }

    @PostMapping("/{taskId}")
    public ResponseEntity<Void> completeTask(@PathVariable("taskId") String taskId, @RequestBody ApproveTaskCompleteDto userInput) {
        VariableMap vars = builder()
            .set(ORDER_APPROVED, userInput.getApproved())
            .build();
        taskService.complete(taskId, vars);
        return ResponseEntity.noContent().build();
    }
}

```

## Testing correct variable access

If you want to write the test for the REST controller, you will need to stub
the task service and verify that the correct variables has been set. To simplify
these tests, we created an additional library module `camunda-bpm-data-test`.
Please put the following dependency into your `pom.xml`:

```xml

<dependency>
  <groupId>io.holunda.data</groupId>
  <artifactId>camunda-bpm-data-test</artifactId>
  <version>${camunda-bpm-data.version}</version>
  <scope>test</scope>
</dependency>
```

Now you can use `TaskServiceVariableMockBuilder` to stub correct behavior of Camunda Task Service
and `TaskServiceVerifier` to verify the correct access to variables easily. Here is the JUnit
test of the REST controller above, making use of `camunda-bpm-data-test`.


```java

public class ApproveOrderTaskControllerTest {

    private static Order order = new Order("ORDER-ID-1", new Date(), new ArrayList<>());
    private TaskService taskService = mock(TaskService.class);
    private TaskServiceMockVerifier verifier = taskServiceMockVerifier(taskService);
    private ApproveOrderTaskController controller = new ApproveOrderTaskController(taskService);
    private String taskId;

    @Before
    public void prepareTest() {
        reset(taskService);
        taskId = UUID.randomUUID().toString();
    }

    @Test
    public void testLoadTask() {
        // given
        taskServiceVariableMockBuilder(taskService).initial(ORDER, order).build();
        // when
        ResponseEntity<ApproveTaskDto> responseEntity = controller.loadTask(taskId);
        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(new ApproveTaskDto(order));
        verifier.verifyGet(ORDER, taskId);
        verifier.verifyNoMoreInteractions();
    }

    @Test
    public void testCompleteTask() {
        // when
        ApproveTaskCompleteDto response = new ApproveTaskCompleteDto(true);
        ResponseEntity<Void> responseEntity = controller.completeTask(taskId, response);
        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verifier.verifyComplete(builder().set(ORDER_APPROVED, response.getApproved()).build(), taskId);
        verifier.verifyNoMoreInteractions();
    }
}
```
