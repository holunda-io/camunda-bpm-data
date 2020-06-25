package io.holunda.camunda.bpm.data.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.junit.Test;

import java.util.List;

import static org.camunda.bpm.model.xml.test.assertions.ModelAssertions.assertThat;

public class JacksonDeserializationTest {

    private static final MyComplexType value1 = new MyComplexType("value", 17);
    private static final MyComplexType value2 = new MyComplexType("value2", 11);

    @Test
    public void deserializeList() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        String json = "[ { \"foo\": \"value\", \"bar\": 17 }, { \"foo\": \"value2\", \"bar\": 11 } ]";

        CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, MyComplexType.class);
        List<MyComplexType> deserialized = objectMapper.readValue(json, collectionType);
        assertThat(deserialized).containsExactlyInAnyOrder(value1, value2);

    }
}
