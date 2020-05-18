package io.holunda.camunda.bpm.data.adapter.wrapped;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class SetWrapperTest {

    private final ObjectMapper om = new ObjectMapper()
        .registerModule(new KotlinModule())
        .registerModule(new JavaTimeModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

    @Test
    public void convert_single() throws JsonProcessingException {
        ComplexType dto = new ComplexType("xxx", LocalDate.now());
        String json = om.writeValueAsString(dto);

        assertThat(om.readValue(json, ComplexType.class)).isEqualTo(dto);
    }

    @Test
    public void convert_set() throws JsonProcessingException {

        ComplexType ct1 = new ComplexType("x1", LocalDate.now());
        ComplexType ct2 = new ComplexType("x2", LocalDate.now().plusDays(1));
        Set<ComplexType> orig = Sets.newSet(ct1, ct2);

        // forth
        String setJson = om.writeValueAsString(orig);
        System.out.println(setJson);

        // and back
        Set<ComplexType> copyFromSet = read(om, setJson, ComplexType.class);

        System.out.println(copyFromSet);
        assertThat(copyFromSet).isEqualTo(orig);
    }

    public static <T> Set<T> read(ObjectMapper om, String json, Class<T> clazz) throws JsonProcessingException {
        return om.readValue(json, om.getTypeFactory().constructCollectionType(Set.class, clazz));
    }

    public static class ComplexType {
        private String name;
        private LocalDate date;

        public ComplexType() {
        }

        public ComplexType(String name, LocalDate date) {
            this.name = name;
            this.date = date;
        }

        public String getName() {
            return name;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ComplexType that = (ComplexType) o;
            return Objects.equals(name, that.name) &&
                Objects.equals(date, that.date);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, date);
        }

        @Override
        public String toString() {
            return "ComplexType{" +
                "name='" + name + '\'' +
                ", date=" + date +
                '}';
        }
    }
}
