package miranda.gabriel.tenus.application.usecases;

import miranda.gabriel.tenus.adapters.inbounds.dto.address.AddressRequestDTO;

public interface AddressUsecases {

    public void registerAddress(Long id, AddressRequestDTO dto, String userId);

    public void updateAddress(Long id, AddressRequestDTO dto, String userId);

}
