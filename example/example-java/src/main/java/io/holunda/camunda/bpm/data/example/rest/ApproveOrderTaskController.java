package io.holunda.camunda.bpm.data.example.rest;


import io.holunda.camunda.bpm.data.example.domain.Order;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.holunda.camunda.bpm.data.example.process.OrderApproval.ORDER;
import static io.holunda.camunda.bpm.data.example.process.OrderApproval.ORDER_APPROVED;
import static org.camunda.bpm.engine.variable.Variables.createVariables;

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
  public ResponseEntity<Void> completeTask(@PathVariable("taskId") String taskId, @RequestBody ApproveTaskCompleteDto approveTaskComplete) {
    VariableMap vars = createVariables();
    ORDER_APPROVED.on(vars).set(approveTaskComplete.getApproved());
    taskService.complete(taskId, vars);
    return ResponseEntity.noContent().build();
  }

}
