package io.holunda.camunda.bpm.data.reader

import io.holunda.camunda.bpm.data.factory.VariableFactory
import org.camunda.bpm.engine.externaltask.LockedExternalTask
import java.util.*

/**
 * Allows reading multiple variable values from [LockedExternalTask]).
 * @param lockedExternalTask scope to operate on.
 */
class LockedExternalTaskReader(private val lockedExternalTask: LockedExternalTask) : VariableReader {
    override fun <T> getOptional(variableFactory: VariableFactory<T>): Optional<T> {
        return variableFactory.from(lockedExternalTask).optional
    }

    override fun <T> get(variableFactory: VariableFactory<T>): T {
        return variableFactory.from(lockedExternalTask).get()
    }

    override fun <T> getLocal(variableFactory: VariableFactory<T>): T {
        return variableFactory.from(lockedExternalTask).local
    }

    override fun <T> getLocalOptional(variableFactory: VariableFactory<T>): Optional<T> {
        return variableFactory.from(lockedExternalTask).localOptional
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false
        val that = other as LockedExternalTaskReader
        return lockedExternalTask == that.lockedExternalTask
    }

    override fun hashCode(): Int {
        return lockedExternalTask.hashCode()
    }
}
