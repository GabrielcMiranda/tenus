package miranda.gabriel.task_planner.adapters.outbounds.entities.embedded;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class PhoneEmbeddable {

    private String value;

    protected PhoneEmbeddable() {
    
    }

    public PhoneEmbeddable(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneEmbeddable)) return false;
        PhoneEmbeddable that = (PhoneEmbeddable) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "PhoneEmbeddable{" +
                "value='" + value + '\'' +
                '}';
    }
}