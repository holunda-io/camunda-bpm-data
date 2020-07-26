package io.holunda.camunda.bpm.data.itest

import com.tngtech.jgiven.Stage
import com.tngtech.jgiven.integration.spring.JGivenStage
import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.assertj.core.api.Assertions
import java.util.*

/**
 * Base assert stage.
 */
@JGivenStage
class AssertStage : Stage<AssertStage>() {

    fun variables_had_value(readValues: Map<String, Any>, variablesWithValue: Set<Pair<VariableFactory<*>, Any>>): AssertStage {
        variablesWithValue.forEach {
            Assertions.assertThat(readValues).containsEntry(it.first.name, it.second)
        }
        return self()
    }

    fun variables_had_not_value(readValues: Map<String, Any>, vararg variableWithValue: VariableFactory<*>): AssertStage {
        val emptyOptional = Optional.empty<Any>()

        variableWithValue.forEach {
            Assertions.assertThat(readValues).containsEntry(it.name, emptyOptional)
        }
        return self()
    }
}
