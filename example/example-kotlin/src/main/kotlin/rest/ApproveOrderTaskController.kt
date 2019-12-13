package io.holunda.camunda.bpm.data.example.kotlin.rest

import io.holunda.camunda.bpm.data.example.kotlin.domain.Order
import io.holunda.camunda.bpm.data.example.kotlin.process.OrderApproval.Variables.ORDER
import io.holunda.camunda.bpm.data.example.kotlin.process.OrderApproval.Variables.ORDER_APPROVED
import org.camunda.bpm.engine.TaskService
import org.camunda.bpm.engine.variable.Variables.createVariables
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/task/approve-order")
class ApproveOrderTaskController(
  private val taskService: TaskService
) {

  @GetMapping("/{taskId}")
  fun loadTask(@PathVariable("taskId") taskId: String): ResponseEntity<ApproveTaskDto> {
    val order = ORDER.from(taskService, taskId).get()
    return ResponseEntity.ok(ApproveTaskDto(order))
  }

  @PostMapping("/{taskId}")
  fun completeTask(@PathVariable("taskId") taskId: String, @RequestBody approveTaskComplete: ApproveTaskCompleteDto): ResponseEntity<Void> {
    val vars = createVariables()
    ORDER_APPROVED.on(vars).set(approveTaskComplete.approved)
    taskService.complete(taskId, vars)
    return ResponseEntity.noContent().build()
  }
}

data class ApproveTaskDto(val order: Order)

data class ApproveTaskCompleteDto(val approved: Boolean)
