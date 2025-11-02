package miranda.gabriel.tenus.adapters.outbounds.geocoding;

import miranda.gabriel.tenus.core.model.address.Address;

public interface GeocodingServicePort {
  
    Address geocodeAddress(Address address);
}
