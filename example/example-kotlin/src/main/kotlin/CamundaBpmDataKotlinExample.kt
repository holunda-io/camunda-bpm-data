package io.holunda.camunda.bpm.data.example

import io.holunda.camunda.bpm.data.example.process.OrderDeliveryInstanceFactory
import mu.KLogging
import org.camunda.bpm.engine.RepositoryService
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

fun main(args: Array<String>) = runApplication<CamundaBpmDataKotlinExampleApplication>(*args).let { Unit }

@SpringBootApplication
@EnableProcessApplication
class CamundaBpmDataKotlinExampleApplication {

  companion object : KLogging()

  @Autowired
  lateinit var orderDeliveryInstanceFactory: OrderDeliveryInstanceFactory

  @Autowired
  lateinit var repositoryService: RepositoryService

  @EventListener
  fun onDeploy(event: PostDeployEvent) {
    orderDeliveryInstanceFactory.start()
  }
}
