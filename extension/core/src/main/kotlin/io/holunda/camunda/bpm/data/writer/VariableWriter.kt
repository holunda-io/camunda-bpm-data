package io.holunda.camunda.bpm.data.writer

/**
 * Inverting calls to [io.holunda.camunda.bpm.data.adapter.WriteAdapter].
 *
 * @param <S> type of concrete Writer for fluent usage.
</S> */
interface VariableWriter<S : VariableWriter<S>> : LocalVariableWriter<S>, GlobalVariableWriter<S>
