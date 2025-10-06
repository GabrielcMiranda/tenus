package miranda.gabriel.tenus.adapters.mappers;

import org.mapstruct.Mapper;

import miranda.gabriel.tenus.adapters.outbounds.entities.JpaAddressEntity;
import miranda.gabriel.tenus.core.model.address.Address;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toDomain(JpaAddressEntity entity);

    JpaAddressEntity toEntity(Address domain);
}
