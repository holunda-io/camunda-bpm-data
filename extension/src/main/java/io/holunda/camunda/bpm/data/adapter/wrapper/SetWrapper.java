package io.holunda.camunda.bpm.data.adapter.wrapper;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SetWrapper<T> implements WrapperType<T> {

    private Set<T> set;

    public SetWrapper() {
        this(new HashSet<>());
    }

    public SetWrapper(Set<T> set) {
        this.set = new HashSet<>(set);
    }

    public Set<T> getSet() {
        return set;
    }

    public void setSet(Set<T> set) {
        this.set = set;
    }

    @Override
    public <C extends Collection<T>> C getAsCollection() {
        return (C) set;
    }

    @Override
    public String toString() {
        return "SetWrapper{" +
            "set=" + set +
            '}';
    }
}
