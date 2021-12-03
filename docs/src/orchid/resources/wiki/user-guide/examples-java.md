---
title: Java Examples
---

## Java Examples

The following example code demonstrates the usage of the library using Java.

### Define variable

``` java
public class OrderApproval {
  public static final VariableFactory<String> ORDER_ID = stringVariable("orderId");
  public static final VariableFactory<Order> ORDER = customVariable("order", Order.class);
  public static final VariableFactory<Boolean> ORDER_APPROVED = booleanVariable("orderApproved");
  public static final VariableFactory<OrderPosition> ORDER_POSITION = customVariable("orderPosition", OrderPosition.class);
  public static final VariableFactory<BigDecimal> ORDER_TOTAL = customVariable("orderTotal", BigDecimal.class);
}
```

### Read variable from Java delegate

``` java
@Configuration
class JavaDelegates {

  @Bean
  public JavaDelegate calculateOrderPositions() {
    return execution -> {
      OrderPosition orderPosition = ORDER_POSITION.from(execution).get();
      Boolean orderApproved = ORDER_APPROVED.from(execution).getLocal();
      Optional<BigDecimal> orderTotal = ORDER_TOTAL.from(execution).getOptional();
    };
  }
}
```

### Write variable from Java delegate

``` java
import java.math.BigDecimal;

@Configuration
class JavaDelegates {

  @Bean
  public JavaDelegate calculateOrderPositions() {
    return execution -> {
      OrderPosition orderPosition = new OrderPosition("Pencil", BigDecimal.valueOf(1.5), 1);
      ORDER_POSITION.on(execution).set(orderPosition);
    };
  }
}
```

### Remove variable from Java delegate

``` java
import java.math.BigDecimal;
@Configuration
class JavaDelegates {

  @Bean
  public JavaDelegate calculateOrderPositions() {
    return execution -> {
      ORDER_APPROVED.on(execution).removeLocal();
    };
  }
}
```

### Update variable from Java delegate

``` java
import java.math.BigDecimal;
@Configuration
class JavaDelegates {

  @Bean
  public JavaDelegate calculateOrderPositions() {
    return execution -> {
      OrderPosition orderPosition = ORDER_POSITION.from(execution).get();
      ORDER_TOTAL.on(execution).updateLocal(amount -> amount.add(orderPosition.getNetCost().multiply(BigDecimal.valueOf(orderPosition.getAmount()))));
    };
  }
}
```

### Fluent API to remove several variables

``` java
@Configuration
class JavaDelegates {

  @Bean
  public ExecutionListener removeProcessVariables() {
    return execution ->
    {
      CamundaBpmData.writer(execution)
          .remove(ORDER_ID)
          .remove(ORDER)
          .remove(ORDER_APPROVED)
          .remove(ORDER_TOTAL)
          .removeLocal(ORDER_POSITIONS);
    };
  }
}
```

### Fluent API to set several variables

``` java
@Component
class SomeService {

  @Autowired
  private RuntimeService runtimeService;
  @Autowired
  private TaskService taskService;

  public void setNewValuesForExecution(String executionId, String orderId, Boolean orderApproved) {
      CamundaBpmData.writer(runtimeService, executionId)
          .set(ORDER_ID, orderId)
          .set(ORDER_APPROVED, orderApproved)
          .update(ORDER_TOTAL, amount -> amount.add(10));
  }

  public void setNewValuesForTask(String taskId, String orderId, Boolean orderApproved) {
      CamundaBpmData.writer(taskService, taskId)
          .set(ORDER_ID, orderId)
          .set(ORDER_APPROVED, orderApproved);
  }

  public void start() {
      VariableMap variables = CamundaBpmData.writer()
          .set(ORDER_ID, "4711")
          .set(ORDER_APPROVED, false)
          .build();
      runtimeService.startProcessInstanceById("myId", "businessKey", variables);
  }
}
```

### Fluent API to read several variables

``` java
@Component
class SomeService {

  @Autowired
  private RuntimeService runtimeService;
  @Autowired
  private TaskService taskService;

  public String readValuesFromExecution(String executionId) {
      VariableReader reader = CamundaBpmData.reader(runtimeService, executionId);
      String orderId = reader.get(ORDER_ID);
      Boolean orderApproved = reader.get(ORDER_APPROVED);
      if (orderApproved) {
          // ...
      }
      return orderId;
  }

  public String readValuesFromTask(String taskId) {
      VariableReader reader = CamundaBpmData.reader(taskService, taskId);
      String orderId = reader.get(ORDER_ID);
      Boolean orderApproved = reader.get(ORDER_APPROVED);
      if (orderApproved) {
          // ...
      }
      return orderId;
  }
}
```


### Anti-Corruption-Layer: Wrap variables to correlate

``` java
@Component
class SomeService {

  private static final AntiCorruptionLayer MESSAGE_ACL = CamundaBpmDataMapper.identityReplace(
      "__transient",
      true
  );

  public void correlate() {
      VariableMap variables = CamundaBpmData.builder()
          .set(ORDER_ID, "4711")
          .set(ORDER_APPROVED, false)
          .build();
      runtimeService.correlateMessage("message_1", MESSAGE_ACL.wrap(variables));
  }
}
```

### Anti-Corruption-Layer: Check and wrap variables to correlate

``` java
@Component
class SomeService {

    private static AntiCorruptionLayer MY_ACL = CamundaBpmDataACL.guardTransformingReplace(
        "__transient",
        true,
        new VariablesGuard(exists(ORDER_ID)),
        IdentityVariableMapTransformer.INSTANCE
    );

  public void correlate() {
      VariableMap variables = CamundaBpmData.builder()
          .set(ORDER_ID, "4711")
          .set(ORDER_APPROVED, false)
          .build();
      runtimeService.correlateMessage("message_1", MESSAGE_ACL.checkAndWrap(variables));
  }
}
```

### Define Guards to validate variables in the process

``` java
@Configuration
class VariableGuardConfiguration {

    public static final String MY_GUARD_BEANNAME = "myGuardBeanName";
        
    @Bean
    public Supplier<Validator>  validatorSupplier() {
        // assuming dependencys to implement javax.validation:validation-api are present
        return () -> Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Bean(VariableGuardConfiguration.MY_GUARD_BEANNAME)
    public ExecutionListener myGuardBeanName(Supplier<Validator> validatorSupplier) {
        return new DefaultGuardExecutionListener(
            Arrays.asList(
                exists(REQUIRED_VALUE),
                notExists(FUTURE_VALUE),
                hasValue(THE_ANSWER, 42),
                hasOneOfValues(MY_DIRECTION, Set.of("left", "up", "down")),
                isEmail(USER_EMAIL),
                isUuid(DOCUMENT_ID),
                matches(DOCUMENT_BODY, this::myDocumentBodyMatcher),
                matches(DOCUMENT_BODY, this::myDocumentBodyMatcher, this::validationMessageSupplier),
                matchesRegex(DOCUMENT_BODY, "^Dude.*", "Starts with 'Dude'"),
                isValidBean(My_DOCUMENT, validatorSupplier)
            ), true);
    }

    private Boolean myDocumentBodyMatcher(String body) {
        return true;
    }    

    private String validationMessageSupplier(VariableFactory<String> variableFactory, String localLabel, Optional<String> option) {
        return String.format("Expecting%s variable '%s' to always match my document body matcher, but its value '%s' has not.", localLabel, variableFactory.getName(), option.orElse(""));
    }
}

class MyDocument {
    @Email
    public String email;    
}
```

### Example project

For more examples, please check-out the Java Example project, at
[Github](https://github.com/holunda-io/camunda-bpm-data/tree/develop/example/example-java)
