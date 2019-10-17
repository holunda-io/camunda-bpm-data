package io.holunda.camunda.bpm.data.adapter;

public interface WriteAdapter<T> {

  void set(T value);

  void set(T value, boolean isTransient);

  void setLocal(T value);

  void setLocal(T value, boolean isTransient);

}
