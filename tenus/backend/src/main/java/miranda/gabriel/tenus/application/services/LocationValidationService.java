package miranda.gabriel.tenus.application.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.core.model.address.Address;
import miranda.gabriel.tenus.infrastructure.exception.TenusExceptions;


@Service
@RequiredArgsConstructor
public class LocationValidationService {

    private static final double MAX_DISTANCE_METERS = 100.0;
    
    private static final double EARTH_RADIUS_METERS = 6371000.0;

    public double validateProximity(Double userLatitude, Double userLongitude, Address taskAddress) {
        
        validateCoordinates(userLatitude, userLongitude, "User location");
        
        if (taskAddress == null) {
            throw new TenusExceptions.BusinessRuleViolationException("Task does not have an address defined");
        }
        
        validateCoordinates(taskAddress.getLatitude(), taskAddress.getLongitude(), "Task address");
        
        double distance = calculateDistance(
            userLatitude, 
            userLongitude, 
            taskAddress.getLatitude(), 
            taskAddress.getLongitude()
        );
        
        if (distance > MAX_DISTANCE_METERS) {
            throw new TenusExceptions.LocationTooFarException(
                String.format("You are %.0f meters away from the task location. Maximum allowed: %.0f meters", 
                    distance, MAX_DISTANCE_METERS)
            );
        }
        return distance;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
    
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;
        
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        
        return EARTH_RADIUS_METERS * c;
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
        
        //erro gps
        if (latitude == 0.0 && longitude == 0.0) {
            throw new TenusExceptions.InvalidLocationException(
                locationName + " has default coordinates (0,0). Please enable GPS and try again"
            );
        }
    }
}
