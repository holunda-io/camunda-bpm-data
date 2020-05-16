package io.holunda.camunda.bpm.data.adapter.wrapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

public class SetWrapperTest {

  private final ObjectMapper om = new ObjectMapper()
    .registerModule(new KotlinModule())
    .registerModule(new JavaTimeModule())
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

  @Test
  public void convert_json() throws JsonProcessingException {
    ComplexType ct1 = new ComplexType("x1", LocalDate.now());
    ComplexType ct2 = new ComplexType("x2", LocalDate.now().plusDays(1));

    Set<ComplexType> orig = Sets.newSet(ct1, ct2);

    SetWrapper<ComplexType> wrapper = new SetWrapper<>(orig);

    String json = om.writeValueAsString(wrapper);

    Set<ComplexType> copy = om.readValue(json, new TypeReference<SetWrapper<ComplexType>>() {
    }).getAsCollection();

    System.out.println(copy);

    assertThat(copy).isEqualTo(orig);
  }

  @Test
  public void convert_single() throws JsonProcessingException {
    ComplexType dto = new ComplexType("xxx", LocalDate.now());
    String json = om.writeValueAsString(dto);

    assertThat(om.readValue(json, ComplexType.class)).isEqualTo(dto);
  }

  public static class ComplexType {
    private  String name;
    private  LocalDate date;

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
