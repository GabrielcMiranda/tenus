package miranda.gabriel.task_planner.utils.mappers;

import org.mapstruct.Mapper;

import miranda.gabriel.task_planner.adapters.outbounds.entities.JpaAddressEntity;
import miranda.gabriel.task_planner.core.model.address.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toDomain(JpaAddressEntity entity);

    JpaAddressEntity toEntity(Address domain);
}
