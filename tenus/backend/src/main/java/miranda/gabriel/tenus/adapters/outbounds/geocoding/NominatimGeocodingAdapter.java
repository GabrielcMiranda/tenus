package miranda.gabriel.tenus.adapters.outbounds.geocoding;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import miranda.gabriel.tenus.core.model.address.Address;

@Service
@RequiredArgsConstructor
@Slf4j
public class NominatimGeocodingAdapter implements GeocodingServicePort {

    private static final String NOMINATIM_API_URL = "https://nominatim.openstreetmap.org/search";
    private static final String USER_AGENT = "TenusApp/1.0";
    
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @Cacheable(value = "geocode", key = "#address.street + ',' + #address.number + ',' + #address.city + ',' + #address.state")
    public Address geocodeAddress(Address address) {
        
        String fullAddress = buildFullAddress(address);
        
        log.info("Geocoding address: {}", fullAddress);
        
        try {
            String url = UriComponentsBuilder.fromHttpUrl(NOMINATIM_API_URL)
                .queryParam("q", fullAddress)
                .queryParam("format", "json")
                .queryParam("limit", "1")
                .queryParam("addressdetails", "1")
                .queryParam("countrycodes", "br")
                .build()
                .toUriString();
            
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", USER_AGENT);
            HttpEntity<String> entity = new HttpEntity<>(headers);
            
            ResponseEntity<NominatimResponse[]> response = 
                restTemplate.exchange(url, HttpMethod.GET, entity, NominatimResponse[].class);
            
            NominatimResponse[] results = response.getBody();
            
            if (results == null || results.length == 0) {
                log.warn("No geocoding results found for address: {}", fullAddress);
                throw new RuntimeException("Could not geocode address: " + fullAddress);
            }
            
            NominatimResponse result = results[0];
            
            address.setLatitude(Double.parseDouble(result.getLat()));
            address.setLongitude(Double.parseDouble(result.getLon()));
            
            log.info("Geocoding successful: lat={}, lon={}", result.getLat(), result.getLon());
            
            //rate limit da API (1 req/s) pra nao quebrar
            Thread.sleep(1000);
            
            return address;
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Geocoding interrupted", e);
        } catch (Exception e) {
            log.error("Error geocoding address: {}", fullAddress, e);
            throw new RuntimeException("Failed to geocode address: " + e.getMessage(), e);
        }
    }

    private String buildFullAddress(Address address) {
        StringBuilder sb = new StringBuilder();
        
        if (address.getStreet() != null) {
            sb.append(address.getStreet());
        }
        
        if (address.getNumber() != null) {
            sb.append(", ").append(address.getNumber());
        }
        
        if (address.getNeighbourhood() != null) {
            sb.append(", ").append(address.getNeighbourhood());
        }
        
        if (address.getCity() != null) {
            sb.append(", ").append(address.getCity());
        }
        
        if (address.getState() != null) {
            sb.append(", ").append(address.getState());
        }
        
        if (address.getZipCode() != null) {
            sb.append(", ").append(address.getZipCode());
        }
        
        sb.append(", Brazil");
        
        return sb.toString();
    }

    @Data
    private static class NominatimResponse {
        private String lat;
        private String lon;
        private String display_name;
    }
}
