package io.holunda.camunda.bpm.data.jackson;

public class MyComplexType {
  private String foo;
  private Integer bar;

  public MyComplexType() {
  }

  public MyComplexType(String foo, Integer bar) {
    this.bar = bar;
    this.foo = foo;
  }

  public String getFoo() {
    return foo;
  }

  public void setFoo(String foo) {
    this.foo = foo;
  }

  public Integer getBar() {
    return bar;
  }

  public void setBar(Integer bar) {
    this.bar = bar;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    MyComplexType that = (MyComplexType) o;

    if (foo != null ? !foo.equals(that.foo) : that.foo != null) {
      return false;
    }
    return bar != null ? bar.equals(that.bar) : that.bar == null;
  }

  @Override
  public int hashCode() {
    int result = foo != null ? foo.hashCode() : 0;
    result = 31 * result + (bar != null ? bar.hashCode() : 0);
    return result;
  }
}
