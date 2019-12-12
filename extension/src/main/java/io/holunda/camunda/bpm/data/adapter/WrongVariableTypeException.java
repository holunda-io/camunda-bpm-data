package io.holunda.camunda.bpm.data.adapter;

/**
 * Exception indicating that a variable has a different type than requested.
 */
public class WrongVariableTypeException extends RuntimeException {
  /**
   * Constructs the exception.
   *
   * @param reason reason.
   */
  public WrongVariableTypeException(String reason) { super(reason); }
}
