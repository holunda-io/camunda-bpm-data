## Typed access to process variables

Camunda BPM engine provide Java API to access the process variables.
This consists of:

* `RuntimeService` methods
* `TaskService` methods
* Methods on `DelegateExecution`
* Methods on `DelegateTask`
* `VariableMap`

All those methods requires the user of the API to know the variable type.
Here is a usage example:


``` java
ProcessInstance processInstance = ...;
List<OrderPosition> orderPositions = (List<OrderPosition>) runtimeService
  .getVariable(processInstance.id, "orderPositions");
```

This leads to problems during refactoring and makes variable access more complicated than it is. This library addresses
this issue and allows for more convenient type-safe process variable access.


More details can be found in:

*  [Data in Process (Part 1)](https://medium.com/holisticon-consultants/data-in-process-part-1-2620bf9abd76)
*  [Data in Process (Part 2)](https://medium.com/holisticon-consultants/data-in-process-part-2-7c6a109e6ee2)

## Variable guards

Process automation often follows strict rules defined by the business. On the other hand, the process execution itself
defines rules in terms of pre- and post-conditions on the process payload (stored as process variables in Camunda BPM).
Rising complexity of the implemented processes makes the compliance to those rules challenging. In order to fulfill the
conditions on process variables during the execution of business processes, a concept of `VariableGuard` is provided by
the library. A guard consists of a set of `VariableConditions` and can be evaluated in all contexts, the variables
are used in: `DelegateTask`, `DelegateExecution`, `TaskService`, `RuntimeService`, `VariableMap`.

Here is an example of a task listener defining a `VariablesGuard` to test that the process variables `ORDER_APPROVED` and 
`APPROVER_ID` are set, which will throw a `GuardViolationException` if the condition is not met.


``` java

import static io.holunda.camunda.bpm.data.guard.CamundaBpmDataGuards.exists;

@Component
class MyGuardListener extends DefaultGuardTaskListener {

    public MyGuardListener() {
        super(new VariablesGuard(List.of(exists(ORDER_APPROVED), exists(APPROVER_ID)), true);
    }
}
```

By default, all conditions of a `VariablesGuard` must be met in order to pass the validations. This behaviour can be explicitly  
defined by passing the `reduceOperator = VariablesGuard.ALL` when creating the `VariablesGuard`. The `reduceOperator` can take 
the following values:

| `reduceOperator`        | Semantics                                    |
|-------------------------|----------------------------------------------|
| `VariablesGuard.ALL`    | All `VariableCondition`s must be met         |
| `VariablesGuard.ONE_OF` | At least ONE `VariableCondition` must be met |

## Anti-Corruption-Layer

If a process is signalled or hit by a correlated message, there is no way to check if the transported variables are set correctly.
In addition, the variables are written directly to the execution of the correlated process instance. In case of a multi-instance
event-base sub-process this will eventually overwrite the values of the main execution.

To prevent all this, a feature called Anti-Corruption-Layer (ACL) is implemented. An ACL is there to protect the execution
from bad modifications and influence the way, the modification is executed. For the protection, an ACL relies on a Variables Guards,
defining conditions to be satisfied. For the influencing of modification, the `VariableMapTransformer` can be used.

To use the ACL layer you will need to change the way you correlate messages (or signal the execution). Instead of supplying the variables
directly to the `correlate` method of the `RuntimeService`, the client is wrapping all variables into a map hold by a single transient variable
and correlate this variable with the process (we call this procedure variable wrapping). On the process side, an execution listener placed
on the end of the catch event is responsible to extract the variable map from the transient variable, check it by passing through the `VariablesGuard`
and finally pass over to the `VariableMapTransformer` to map from external to internal representation.

Here is the code, required on the client side to correlate the message.

``` java
@Component
class SomeService {

    private static AntiCorruptionLayer MY_ACL = CamundaBpmDataACL.guardTransformingReplace(
        "__transient", // name of the transient variable for wrapping
        true, // if passes the guard, write to local scope
        new VariablesGuard(exists(ORDER_ID)), // guard defining condition on ORDER_ID
        IdentityVariableMapTransformer.INSTANCE // use 1:1 transformer
                                                // write the variables without modifications
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

On the process side, the BPMN message catch event should have an `End` listener responsible for unwrapping the values. If the listener is
implemented as a Spring Bean bounded via delegate expression `${messageAclListener}` then the following code is responsible for providing such a listener:

``` java
@Configuration
class SomeConfiguration {

    private static AntiCorruptionLayer MY_ACL = CamundaBpmDataACL.guardTransformingReplace(
        "__transient", // name of the transient variable for wrapping
        true, // if passes the guard, write to local scope
        new VariablesGuard(exists(ORDER_ID)), // guard defining condition on ORDER_ID
        IdentityVariableMapTransformer.INSTANCE // use 1:1 transformer
                                                // write the variables without modifications
    );

    @Bean("messageAclListener")
    public ExecutionListener messageAclListener() {
        return MY_ACL.getExecutionListener();
    }
}
```

Such a setup will only allow to correlate messages, if the variables provided include a value for the `ORDER_ID`. It will write all
variables provided (`ORDER_ID` and `ORDER_APPROVED`) into a local scope of the execution.
