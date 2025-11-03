package miranda.gabriel.tenus.core.model.address;

public interface AddressRepository {

    Address save (Address address);
  
    Double calculateDistanceInMeters(Long addressId, Double userLatitude, Double userLongitude);
}
