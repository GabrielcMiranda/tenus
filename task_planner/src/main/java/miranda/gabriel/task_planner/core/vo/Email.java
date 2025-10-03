package miranda.gabriel.task_planner.core.vo;

import java.util.Objects;

public class Email {

    private String value;

    protected Email(){
        
    }

    public Email(String value){
        if(value == null || !value.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            throw new IllegalArgumentException("invalid email");
        }

        this.value = value;
    }

    public String getValue(){
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email = (Email) o;
        return value.equals(email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
