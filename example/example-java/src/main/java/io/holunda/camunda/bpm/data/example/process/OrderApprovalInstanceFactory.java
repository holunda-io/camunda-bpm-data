package io.holunda.camunda.bpm.data.example.process;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.variable.VariableMap;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static io.holunda.camunda.bpm.data.example.process.OrderApproval.ORDER_ID;
import static org.camunda.bpm.engine.variable.Variables.createVariables;

@Component
public class OrderApprovalInstanceFactory {

    private final RuntimeService runtimeService;

    public OrderApprovalInstanceFactory(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public OrderApprovalInstance start() {
        VariableMap vars = createVariables();
        ORDER_ID.on(vars).set("1");
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(OrderApproval.KEY, "order-" + UUID.randomUUID().toString(), vars);
        return new OrderApprovalInstance(instance);
    }

}
