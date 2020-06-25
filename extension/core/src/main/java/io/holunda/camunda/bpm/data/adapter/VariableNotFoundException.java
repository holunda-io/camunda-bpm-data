package io.holunda.camunda.bpm.data.adapter;

/**
 * Exception indicating that a variable is not found.
 */
public class VariableNotFoundException extends RuntimeException {
    /**
     * Constructs the exception.
     *
     * @param reason reason.
     */
    VariableNotFoundException(String reason) {
        super(reason);
    }

}
