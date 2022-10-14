package io.holunda.camunda.bpm.data.example.rest;

import static io.holunda.camunda.bpm.data.CamundaBpmData.builder;
import static io.holunda.camunda.bpm.data.example.process.OrderApproval.*;

import io.holunda.camunda.bpm.data.example.domain.Order;
import java.math.BigDecimal;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.variable.VariableMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task/approve-order")
public class ApproveOrderTaskController {

  private final TaskService taskService;

  public ApproveOrderTaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping("/{taskId}")
  public ResponseEntity<ApproveTaskDto> loadTask(@PathVariable("taskId") String taskId) {
    final Order order = ORDER.from(taskService, taskId).get();
    final BigDecimal orderTotal = ORDER_TOTAL.from(taskService, taskId).get();
    return ResponseEntity.ok(new ApproveTaskDto(order, orderTotal));
  }

  @PostMapping("/{taskId}")
  public ResponseEntity<Void> completeTask(
      @PathVariable("taskId") String taskId, @RequestBody ApproveTaskCompleteDto userInput) {
    VariableMap vars = builder().set(ORDER_APPROVED, userInput.getApproved()).build();
    taskService.complete(taskId, vars);
    return ResponseEntity.noContent().build();
  }
}
