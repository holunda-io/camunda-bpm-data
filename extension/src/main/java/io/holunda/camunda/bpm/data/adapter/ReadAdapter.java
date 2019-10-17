package io.holunda.camunda.bpm.data.adapter;

import java.util.Optional;

public interface ReadAdapter<T> {

  T get();

  Optional<T> getOptional();

}
