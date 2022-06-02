## Camunda BPM Data

[![stable](https://img.shields.io/badge/lifecycle-STABLE-green.svg)](https://github.com/holisticon#open-source-lifecycle)
[![Build Status](https://github.com/holunda-io/camunda-bpm-data/workflows/default/badge.svg)](https://github.com/holunda-io/camunda-bpm-data/actions)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.holunda.data/camunda-bpm-data/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.holunda.data/camunda-bpm-data)
[![CodeCov](https://codecov.io/gh/holunda-io/camunda-bpm-data/branch/master/graph/badge.svg)](https://codecov.io/gh/holunda-io/camunda-bpm-data)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/02d238f71a8243cb96fd2fe322a710eb)](https://www.codacy.com/gh/holunda-io/camunda-bpm-data/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=holunda-io/camunda-bpm-data&amp;utm_campaign=Badge_Grade)
[![Changes](https://img.shields.io/badge/CHANGES---yellow)](https://www.holunda.io/camunda-bpm-data/changelog)
[![gitter](https://badges.gitter.im/holunda-io/camunda-bpm-data.svg)](https://gitter.im/holunda-io/camunda-bpm-data?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)

> Beautiful process data handling for Camunda Platform 7.

## Why to use this library in every Camunda project

If you are a software engineer and run process automation projects in your company or on behalf of the customer
based on Camunda Process Engine, you probably are familiar with process variables. Camunda offers an API to access
them and thereby manipulate the state of the process execution - one of the core features during process automation.

Unfortunately, as a user of the Camunda Platform 7 API, you have to exactly know the variable type (so the Java class behind it).
For example, if you store a String in a variable `"orderId"` you must extract it as a String in every piece of code.
Since there is no code connection between the different code parts, but the BPMN process model orchestrates
these snippets to a single process execution, it makes refactoring and testing of process automation projects
error-prone and challenging.

This library helps you to overcome these difficulties and make access, manipulation and testing process variables really
easy and convenient. We leverage the Camunda Platform 7 API and offer you not only a better API but also some [additional features](https://www.holunda.io/camunda-bpm-data/snapshot/user-guide/features.html).

If you want to read more about data in Camunda processes, have a look on those articles:

  * [Camunda Nation Podcast - Managing Data in Processes, with Simon Zambrovski](https://podcasts.apple.com/us/podcast/managing-data-in-processes-with-simon-zambrovski/id1478382505?i=1000547023972)
  * [Data in Process (Part 1)](https://medium.com/holisticon-consultants/data-in-process-part-1-2620bf9abd76)
  * [Data in Process (Part 2)](https://medium.com/holisticon-consultants/data-in-process-part-2-7c6a109e6ee2)

## Quick Introduction

### Setup

If you just want to start using the library, put the following dependency into your project `pom.xml`:

``` xml
<dependency>
  <groupId>io.holunda.data</groupId>
  <artifactId>camunda-bpm-data</artifactId>
  <version>1.2.6</version>
</dependency>
```

If you are using Gradle Kotlin DSL add to your `build.gradle.kts`:

``` kotlin
implementation("io.holunda.data:camunda-bpm-data:1.2.6")
```

For Gradle Groovy DSL add to your `build.gradle`:

``` groovy
implementation 'io.holunda.data:camunda-bpm-data:1.2.6'
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
        VariableMap vars = CamundaBpmData.builder()
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
  <version>1.2.6</version>
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

For further details, please consult our [Quick Start](https://www.holunda.io/camunda-bpm-data/snapshot/quick-start)
guide or have a look to our primary documentation: [User Guide](https://www.holunda.io/camunda-bpm-data/snapshot/user-guide/motivation.html)

## Working Example

We prepared some typical usage scenarios and implemented two example projects in Java and Kotlin.
See our [Examples](https://www.holunda.io/camunda-bpm-data/snapshot/user-guide/examples.html) section for usage and configuration.

## License

[![Apache License 2](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

This library is developed under Apache 2.0 License.

## Contribution

If you want to contribute to this project, feel free to do so. Start with [Contributing guide](http://holunda.io/camunda-bpm-data/snapshot/developer-guide/contribution.html).

## Maintainer

[<img alt="zambrovski" src="https://avatars.githubusercontent.com/u/673128?v=4&s=117 width=117">](https://github.com/zambrovski) |[<img alt="jangalinski" src="https://avatars.githubusercontent.com/u/814032?v=4&s=117 width=117">](https://github.com/jangalinski) |[<img alt="christian-maschmann" src="https://avatars.githubusercontent.com/u/44058891?v=4&s=117 width=117">](https://github.com/christian-maschmann) |[<img alt="stefanzilske" src="https://avatars.githubusercontent.com/u/10954564?v=4&s=117 width=117">](https://github.com/stefanzilske) |[<img alt="nernsting" src="https://avatars.githubusercontent.com/u/1822388?v=4&s=117 width=117">](https://github.com/nernsting) |[<img alt="pschalk" src="https://avatars.githubusercontent.com/u/8512329?v=4&s=117 width=117">](https://github.com/pschalk) |[<img alt="srsp" src="https://avatars.githubusercontent.com/u/1210541?v=4&s=117 width=117">](https://github.com/srsp) |
:---:|:---:|:---:|:---:|:---:|:---:|:---:|
[zambrovski](https://github.com/zambrovski)|[jangalinski](https://github.com/jangalinski)|[christian-maschmann](https://github.com/christian-maschmann)|[stefanzilske](https://github.com/stefanzilske)|[nernsting](https://github.com/nernsting)|[pschalk](https://github.com/pschalk)|[srsp](https://github.com/srsp)|
