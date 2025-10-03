package miranda.gabriel.task_planner.adapters.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaUserEntity;
import miranda.gabriel.task_planner.adapters.outbounds.entities.embedded.EmailEmbeddable;
import miranda.gabriel.task_planner.adapters.outbounds.entities.embedded.PhoneEmbeddable;
import miranda.gabriel.task_planner.core.model.user.User;
import miranda.gabriel.task_planner.core.vo.Email;
import miranda.gabriel.task_planner.core.vo.Phone;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "boards", ignore = true)
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    User toDomain(JpaUserEntity entity);

    @Mapping(target = "boards", ignore = true)
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    JpaUserEntity toEntity(User domain);

    default Email emailEmbeddableToEmail(EmailEmbeddable emailEmbeddable) {
        if (emailEmbeddable == null || emailEmbeddable.getValue() == null) {
            return null;
        }
        return new Email(emailEmbeddable.getValue());
    }

    default EmailEmbeddable emailToEmailEmbeddable(Email email) {
        if (email == null) {
            return null;
        }
        return new EmailEmbeddable(email.getValue());
    }

    default Phone phoneEmbeddableToPhone(PhoneEmbeddable phoneEmbeddable) {
        if (phoneEmbeddable == null || phoneEmbeddable.getValue() == null) {
            return null;
        }
        return new Phone(phoneEmbeddable.getValue());
    }

    default PhoneEmbeddable phoneToPhoneEmbeddable(Phone phone) {
        if (phone == null) {
            return null;
        }
        return new PhoneEmbeddable(phone.getValue());
    }
}
