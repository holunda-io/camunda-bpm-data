[![Build Status](https://github.com/holunda-io/camunda-bpm-data/workflows/default/badge.svg)](https://github.com/holunda-io/camunda-bpm-data/actions)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.holunda.data/camunda-bpm-data/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.holunda.data/camunda-bpm-data)
[![CodeCov](https://codecov.io/gh/holunda-io/camunda-bpm-data/branch/master/graph/badge.svg)](https://codecov.io/gh/holunda-io/camunda-bpm-data)
[![Codacy](https://api.codacy.com/project/badge/Grade/653136bd5cad48c8a9f2621ee304ff26)](https://app.codacy.com/app/zambrovski/camunda-bpm-data?utm_source=github.com&utm_medium=referral&utm_content=holunda-io/camunda-bpm-data&utm_campaign)
[![Changes](https://img.shields.io/badge/CHANGES---yellow)](https://www.holunda.io/camunda-bpm-data/changelog)
[![gitter](https://badges.gitter.im/holunda-io/camunda-bpm-data.svg)](https://gitter.im/holunda-io/camunda-bpm-data?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

## Camunda BPM Data

> Beautiful process data handling for Camunda BPM.

## Why to use this library in every Camunda project?

If you are a software engineer and run process automation projects in your company or on behalf of the customer
based on Camunda Process Engine, you probably are familiar with process variables. Camunda offers an API to access
them and thereby manipulate the state of the process execution - one of the core features during process automation.

Unfortunately, as a user of the Camunda API, you have to exactly know the variable type (so the Java class behind it).
For example, if you store a String in a variable `"orderId"` you must extract it as a String in every piece of code.
Since there is no code connection between the different code parts, but the BPMN process model orchestrates
these snippets to a single process execution, it makes refactoring and testing of process automation projects
error-prone and challenging.

This library helps you to overcome these difficulties and make access, manipulation and testing process variables really
easy and convenient. We leverage the Camunda API and offer you not only a better API but also some [additional features](https://www.holunda.io/camunda-bpm-data/wiki/user-guide/features).

If you want to read more about data in Camunda processes, have a look on those articles:

- [Data in Process (Part 1)](https://medium.com/holisticon-consultants/data-in-process-part-1-2620bf9abd76)
- [Data in Process (Part 2)](https://medium.com/holisticon-consultants/data-in-process-part-2-7c6a109e6ee2)


## Quick Introduction

### Setup

If you just want to start using the library, put the following dependency into your project `pom.xml`:

``` xml
<dependency>
  <groupId>io.holunda.data</groupId>
  <artifactId>camunda-bpm-data</artifactId>
  <version>1.2.4</version>
</dependency>
```

If you are using Gradle Kotlin DSL add to your `build.gradle.kts`:

``` kotlin
implementation("io.holunda.data:camunda-bpm-data:1.2.4")
```

For Gradle Groovy DSL add to your `build.gradle`:

``` groovy
implementation 'io.holunda.data:camunda-bpm-data:1.2.4'
```

### Variable declaration
Now your setup is completed, and you can declare your variables like this:


``` java
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import static io.holunda.camunda.bpm.data.CamundaBpmData.*;

public class OrderApproval {
  public static final VariableFactory<String> ORDER_ID = stringVariable("orderId");
  public static final VariableFactory<Order> ORDER = customVariable("order", Order.class);
  public static final VariableFactory<Boolean> ORDER_APPROVED = booleanVariable("orderApproved");
  public static final VariableFactory<BigDecimal> ORDER_TOTAL = customVariable("orderTotal", BigDecimal.class);
  public static final VariableFactory<OrderPosition> ORDER_POSITION = customVariable("orderPosition", OrderPosition.class);
}
```

### Variable access from Java Delegate

Finally, you want to access them from your Java delegates, Execution or Task Listeners or simple Java components:

``` java
public class MyDelegate implements JavaDelegate {
  @Override
  public void execute(DelegateExecution execution) {
    VariableReader reader = CamundaBpmData.reader(execution);
    OrderPosition orderPosition = reader.get(ORDER_POSITION);
    BigDecimal oldTotal = reader.getOptional(ORDER_TOTAL).orElse(BigDecimal.ZERO);

    BigDecimal newTotal = oldTotal.add(calculatePrice(orderPosition));
    ORDER_TOTAL.on(execution).setLocal(newTotal);
  }

  private BigDecimal calculatePrice(OrderPosition orderPosition) {
     return orderPosition.getNetCost().multiply(BigDecimal.valueOf(orderPosition.getAmount()));
  }
}
```

### Variable access from REST Controller

Now imagine you are implementing a REST controller for a user task form which
loads data from the process application, displays it, captures some input and
sends that back to the process application to complete the user task. By doing so,
you will usually need to access process variables. Here is an example:

``` java
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

### Testing correct variable access

If you want to write the test for the REST controller, you will need to stub
the task service and verify that the correct variables has been set. To simplify
these tests, we created an additional library module `camunda-bpm-data-test`.
Please put the following dependency into your `pom.xml`:

``` xml
<dependency>
  <groupId>io.holunda.data</groupId>
  <artifactId>camunda-bpm-data-test</artifactId>
  <version>1.2.2</version>
  <scope>test</scope>
</dependency>
```

Now you can use `TaskServiceVariableMockBuilder` to stub correct behavior of Camunda Task Service
and `TaskServiceVerifier` to verify the correct access to variables easily. Here is the JUnit
test of the REST controller above, making use of `camunda-bpm-data-test`.


``` java
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

### Further documentation

For further details, please consult our [Quick Start](https://www.holunda.io/camunda-bpm-data/quick-start)
guide or have a look to our primary documentation - [the User Guide](https://www.holunda.io/camunda-bpm-data/wiki/user-guide)

## Working Example

We prepared some typical usage scenarios and implemented two example projects in Java and Kotlin.
See our [Examples](https://www.holunda.io/camunda-bpm-data/wiki/user-guide/examples) section for usage and configuration.

## License

[![Apache License 2](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.holunda.io/camunda-bpm-data/license)

This library is developed under Apache 2.0 License.


## Contribution

If you want to contribute to this project, feel free to do so. Start with [Contributing guide](http://holunda.io/camunda-bpm-data/wiki/developer-guide/contribution).

## Maintainer

* [Simon Zambrovski](https://gihub.com/zambrovski)
* [Christian Maschmann](https://github.com/christian-maschmann)
* [Jan Galinski](https://github.com/jangalinski)
* [Stefan Zilske](https://github.com/stefanzilske)
