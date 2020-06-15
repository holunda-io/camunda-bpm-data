package io.holunda.camunda.bpm.data.spin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.camunda.spin.impl.json.jackson.format.JacksonJsonDataFormat;
import org.camunda.spin.spi.DataFormatConfigurator;

/**
 * Spin configurator referenced in META-INF/services/org.camunda.spin.spi.DataFormatConfigurator.
 * Configures the {@link ErasedCollectionTypeDetector}.
 */
public class SpinTypeDetectorConfigurator implements DataFormatConfigurator<JacksonJsonDataFormat> {

    @Override
    public Class<JacksonJsonDataFormat> getDataFormatClass() {
        return JacksonJsonDataFormat.class;
    }

    @Override
    public void configure(JacksonJsonDataFormat dataFormat) {
        dataFormat.addTypeDetector(ErasedCollectionTypeDetector.INSTANCE);
    }
}
