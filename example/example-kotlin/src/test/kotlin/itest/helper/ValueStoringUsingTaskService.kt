package io.holunda.camunda.bpm.data.itest.helper

import io.holunda.camunda.bpm.data.itest.CamundaBpmDataITestBase
import org.camunda.bpm.engine.TaskService
import org.springframework.stereotype.Component
import java.util.HashMap

@Component
class ValueStoringUsingTaskService {

  val vars = HashMap<String, Any>()

  fun readValues(taskService: TaskService, taskId: String) {
    vars[CamundaBpmDataITestBase.STRING_VAR.name] = CamundaBpmDataITestBase.STRING_VAR.from(taskService, taskId).get()
    vars[CamundaBpmDataITestBase.DATE_VAR.name] = CamundaBpmDataITestBase.DATE_VAR.from(taskService, taskId).get()
    vars[CamundaBpmDataITestBase.SHORT_VAR.name] = CamundaBpmDataITestBase.SHORT_VAR.from(taskService, taskId).get()
    vars[CamundaBpmDataITestBase.INT_VAR.name] = CamundaBpmDataITestBase.INT_VAR.from(taskService, taskId).get()
    vars[CamundaBpmDataITestBase.LONG_VAR.name] = CamundaBpmDataITestBase.LONG_VAR.from(taskService, taskId).get()
    vars[CamundaBpmDataITestBase.DOUBLE_VAR.name] = CamundaBpmDataITestBase.DOUBLE_VAR.from(taskService, taskId).get()
    vars[CamundaBpmDataITestBase.BOOLEAN_VAR.name] = CamundaBpmDataITestBase.BOOLEAN_VAR.from(taskService, taskId).get()
    vars[CamundaBpmDataITestBase.COMPLEX_VAR.name] = CamundaBpmDataITestBase.COMPLEX_VAR.from(taskService, taskId).get()
    vars[CamundaBpmDataITestBase.LIST_STRING_VAR.name] = CamundaBpmDataITestBase.LIST_STRING_VAR.from(taskService, taskId).get()
    vars[CamundaBpmDataITestBase.SET_STRING_VAR.name] = CamundaBpmDataITestBase.SET_STRING_VAR.from(taskService, taskId).get()
    vars[CamundaBpmDataITestBase.MAP_STRING_DATE_VAR.name] = CamundaBpmDataITestBase.MAP_STRING_DATE_VAR.from(taskService, taskId).get()
  }
}
