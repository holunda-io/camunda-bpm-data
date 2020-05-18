package io.holunda.camunda.bpm.data.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.spin.DataFormats;
import org.camunda.spin.impl.json.jackson.format.JacksonJsonDataFormat;
import org.camunda.spin.spi.DataFormat;

import java.util.function.Supplier;

public class SpinObjectMapperSupplier implements Supplier<ObjectMapper> {

    private final ObjectMapper objectMapper;

    /**
     * Creates a supplier trying to detect the object mapper from SPIN configuration.
     */
    public SpinObjectMapperSupplier() {
        this(null);
    }
    /**
     * Constructs supplier.
     * @param objectMapper object mapper or <code>null</code>.
     */
    public SpinObjectMapperSupplier(ObjectMapper objectMapper) {
        if (objectMapper != null) {
            this.objectMapper = objectMapper;
        } else {
            @SuppressWarnings("rawtypes") final DataFormat format = DataFormats.getDataFormat(DataFormats.JSON_DATAFORMAT_NAME);
            if (format instanceof JacksonJsonDataFormat) {
                this.objectMapper = ((JacksonJsonDataFormat) format).getObjectMapper();
            } else {
                this.objectMapper = new ObjectMapper();
                this.objectMapper.findAndRegisterModules();
            }
        }
    }

    @Override
    public ObjectMapper get() {
        return this.objectMapper;
    }
}
