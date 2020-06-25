package io.holunda.camunda.bpm.data.writer;

import io.holunda.camunda.bpm.data.adapter.WriteAdapter;
import io.holunda.camunda.bpm.data.factory.VariableFactory;
import org.camunda.bpm.engine.variable.VariableMap;
import org.jetbrains.annotations.NotNull;

/**
 * Inverting calls to {@link WriteAdapter}.
 *
 * @param <S> type of concrete Writer for fluent usage.
 */
public interface VariableWriter<S extends VariableWriter<S>> extends LocalVariableWriter<S>, GlobalVariableWriter<S> {

}
