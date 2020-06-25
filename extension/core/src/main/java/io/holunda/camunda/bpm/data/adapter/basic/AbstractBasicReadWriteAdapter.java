package io.holunda.camunda.bpm.data.adapter.basic;

import io.holunda.camunda.bpm.data.adapter.AbstractReadWriteAdapter;
import io.holunda.camunda.bpm.data.adapter.ValueWrapperUtil;
import io.holunda.camunda.bpm.data.adapter.WrongVariableTypeException;
import org.camunda.bpm.engine.variable.value.TypedValue;

/**
 * Base class for all basic read-write-adapter.
 *
 * @param <T> variable type.
 */
public abstract class AbstractBasicReadWriteAdapter<T> extends AbstractReadWriteAdapter<T> {
    /**
     * Variable type.
     */
    protected final Class<T> clazz;

    /**
     * Constructs the adapter.
     *
     * @param variableName name of the variable.
     * @param clazz        variable type.
     */
    public AbstractBasicReadWriteAdapter(String variableName, Class<T> clazz) {
        super(variableName);
        this.clazz = clazz;
    }

    /**
     * Retrieves the value or null.
     *
     * @param value raw value.
     *
     * @return value or null.
     */
    @SuppressWarnings("unchecked")
    protected T getOrNull(Object value) {

        if (value == null) {
            return null;
        }

        if (clazz.isAssignableFrom(value.getClass())) {
            return (T) value;
        }
        throw new WrongVariableTypeException("Error reading " + variableName + ": Couldn't read value of " + clazz + " from " + value);
    }

    @Override
    public TypedValue getTypedValue(Object value, boolean isTransient) {
        return ValueWrapperUtil.getTypedValue(clazz, value, isTransient);
    }
}
