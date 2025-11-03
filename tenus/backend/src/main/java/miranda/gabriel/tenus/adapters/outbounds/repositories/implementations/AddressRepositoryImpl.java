package miranda.gabriel.tenus.adapters.outbounds.repositories.implementations;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.mappers.AddressMapper;
import miranda.gabriel.tenus.adapters.outbounds.repositories.JpaAddressRepository;
import miranda.gabriel.tenus.core.model.address.Address;
import miranda.gabriel.tenus.core.model.address.AddressRepository;

@Repository
@RequiredArgsConstructor
public class AddressRepositoryImpl implements AddressRepository{

    private final JpaAddressRepository jpaAddressRepository;
    private final AddressMapper addressMapper;

    @Override
    public Address save(Address address) {
        var entity = addressMapper.toEntity(address);
        var savedEntity = jpaAddressRepository.save(entity);
        return addressMapper.toDomain(savedEntity);
    }

    @Override
    public Double calculateDistanceInMeters(Long addressId, Double userLatitude, Double userLongitude) {
        return jpaAddressRepository.calculateDistanceInMeters(addressId, userLatitude, userLongitude);
    }
}
