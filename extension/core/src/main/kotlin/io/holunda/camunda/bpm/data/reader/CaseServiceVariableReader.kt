package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.CaseService
import java.util.*

/**
 * Allows reading multiple variable values from [CaseService.getVariable].
 * @param caseService     runtime service to use.
 * @param caseExecutionId execution id.
 *
 */
class CaseServiceVariableReader(
  private val caseService: CaseService,
  private val caseExecutionId: String
) : VariableReader {

    override fun <T> getOptional(variableFactory: VariableFactory<T>): Optional<T> {
        return variableFactory.from(caseService, caseExecutionId).optional
    }

    override fun <T> get(variableFactory: VariableFactory<T>): T {
        return variableFactory.from(caseService, caseExecutionId).get()
    }

    override fun <T> getLocal(variableFactory: VariableFactory<T>): T {
        return variableFactory.from(caseService, caseExecutionId).local
    }

    override fun <T> getLocalOptional(variableFactory: VariableFactory<T>): Optional<T> {
        return variableFactory.from(caseService, caseExecutionId).localOptional
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as CaseServiceVariableReader
        return if (caseService != that.caseService) false else caseExecutionId == that.caseExecutionId
    }

    override fun hashCode(): Int {
        var result = caseService.hashCode()
        result = 31 * result + caseExecutionId.hashCode()
        return result
    }
}
