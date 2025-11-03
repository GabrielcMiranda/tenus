package miranda.gabriel.tenus.adapters.outbounds.location;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miranda.gabriel.tenus.core.model.address.Address;
import miranda.gabriel.tenus.core.model.address.AddressRepository;
import miranda.gabriel.tenus.infrastructure.exception.TenusExceptions;


@Service
@RequiredArgsConstructor
@Slf4j
public class PostGISLocationValidationAdapter implements LocationValidationServicePort {

    private static final double MAX_DISTANCE_METERS = 100.0;
    
    private final AddressRepository addressRepository;

    @Override
    public double validateProximity(Double userLatitude, Double userLongitude, Address taskAddress) {
        
        log.debug("Validating proximity using PostGIS: user({}, {}) vs address({})", 
            userLatitude, userLongitude, taskAddress.getId());
        
        validateCoordinates(userLatitude, userLongitude, "User location");
        
        validateCoordinates(taskAddress.getLatitude(), taskAddress.getLongitude(), "Task address");
        
        Double distance = addressRepository.calculateDistanceInMeters(
            taskAddress.getId(),
            userLatitude, 
            userLongitude
        );
        
        if (distance == null) {
            log.error("PostGIS returned null distance for address {}", taskAddress.getId());
            throw new TenusExceptions.InvalidLocationException("Failed to calculate distance");
        }
        
        log.info("PostGIS calculated distance: {} meters", distance);
        
        if (distance > MAX_DISTANCE_METERS) {
            throw new TenusExceptions.LocationTooFarException(
                String.format("You are %.0f meters away from the task location. Maximum allowed: %.0f meters", 
                    distance, MAX_DISTANCE_METERS)
            );
        }
        
        return distance;
    }

    private void validateCoordinates(Double latitude, Double longitude, String locationName) {
        if (latitude == null || longitude == null) {
            throw new TenusExceptions.InvalidLocationException(
                locationName + " coordinates are missing"
            );
        }
        
        if (latitude < -90 || latitude > 90) {
            throw new TenusExceptions.InvalidLocationException(
                locationName + " has invalid latitude: " + latitude + " (must be between -90 and 90)"
            );
        }
        
        if (longitude < -180 || longitude > 180) {
            throw new TenusExceptions.InvalidLocationException(
                locationName + " has invalid longitude: " + longitude + " (must be between -180 and 180)"
            );
        }
        
        // erro gps
        if (latitude == 0.0 && longitude == 0.0) {
            throw new TenusExceptions.InvalidLocationException(
                locationName + " has default coordinates (0,0). Please enable GPS and try again"
            );
        }
    }
}
