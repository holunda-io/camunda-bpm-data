package io.holunda.camunda.bpm.data.adapter.wrapper;

import java.util.Collection;

public interface WrapperType<T> {
    <C extends Collection<T>> C getAsCollection();
}
