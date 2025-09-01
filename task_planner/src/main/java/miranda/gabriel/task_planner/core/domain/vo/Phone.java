package miranda.gabriel.task_planner.core.domain.vo;

import java.util.Objects;

public class Phone {

    private final String value;

    public Phone(String value) {
        if (value == null || !value.matches("^\\+?[0-9]{10,15}$")) {
            throw new IllegalArgumentException("Invalid phone");
        }
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phone)) return false;
        Phone phone = (Phone) o;
        return value.equals(phone.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}


