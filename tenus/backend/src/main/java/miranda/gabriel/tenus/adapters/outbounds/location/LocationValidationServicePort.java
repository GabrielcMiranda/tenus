package miranda.gabriel.tenus.adapters.outbounds.location;

import miranda.gabriel.tenus.core.model.address.Address;

public interface LocationValidationServicePort {
    
    double validateProximity(Double userLatitude, Double userLongitude, Address taskAddress);
}
