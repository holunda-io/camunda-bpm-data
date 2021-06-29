package io.holunda.camunda.bpm.data.example.noengine

import io.holunda.camunda.bpm.data.CamundaBpmData
import io.holunda.camunda.bpm.data.CamundaBpmData.builder
import io.holunda.camunda.bpm.data.CamundaBpmData.reader
import io.holunda.camunda.bpm.data.CamundaBpmDataKotlin
import io.holunda.camunda.bpm.data.example.noengine.domain.Order
import io.holunda.camunda.bpm.data.example.noengine.domain.OrderPosition
import io.holunda.camunda.bpm.data.factory.VariableFactory
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.Instant.now
import java.util.*

fun main(args: Array<String>) = runApplication<CamundaBpmDataExampleNoEngineApplication>(*args).let { Unit }


val ORDER_ID = CamundaBpmData.stringVariable("orderId")
val ORDER: VariableFactory<Order> = CamundaBpmDataKotlin.customVariable("order")


@SpringBootApplication
class CamundaBpmDataExampleNoEngineApplication: ApplicationRunner {

  @Autowired
  lateinit var variableAwareComponent: VariableAwareComponent


  override fun run(args: ApplicationArguments) {
    variableAwareComponent.readAndWriteSomeVariables()
  }

}

@Component
class VariableAwareComponent {

  companion object: KLogging()

  fun readAndWriteSomeVariables(): Boolean {

    val position = OrderPosition("Pencil", BigDecimal.valueOf(10.00), 3)

    val vars = builder()
      .set(ORDER_ID, "4711")
      .set(ORDER, Order("4711", Date.from(now()), listOf(position)))
      .build()

    ORDER_ID.from(vars).get()

    val reader = reader(vars)
    val order = reader.get(ORDER)

    logger.info { "Successfully read $order" }

    return true
  }

}
