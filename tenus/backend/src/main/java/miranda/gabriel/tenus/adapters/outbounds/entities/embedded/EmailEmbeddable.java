package miranda.gabriel.tenus.adapters.outbounds.entities.embedded;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class EmailEmbeddable {

    private String value;

    protected EmailEmbeddable() {
    
    }

    public EmailEmbeddable(String value) {
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
        if (!(o instanceof EmailEmbeddable)) return false;
        EmailEmbeddable that = (EmailEmbeddable) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "EmailEmbeddable{" +
                "value='" + value + '\'' +
                '}';
    }
}