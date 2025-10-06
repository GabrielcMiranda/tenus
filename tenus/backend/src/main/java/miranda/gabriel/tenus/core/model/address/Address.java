package miranda.gabriel.tenus.core.model.address;

public class Address {

    public Address(Long id, String street, Long number, String neighbourhood, String city, String state, String zipCode,
            String complement, Double latitude, Double longitude) {
        if (zipCode == null || zipCode.length() < 8) {
            throw new IllegalArgumentException("invalid zipcode");
        }
        this.id = id;
        this.street = street;
        this.number = number;
        this.neighbourhood = neighbourhood;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.complement = complement;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private Long id;

    private String street;

    private Long number;

    private String neighbourhood;

    private String city;

    private String state;

    private String zipCode;

    private String complement;

    private Double latitude;

    private Double longitude;

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String fullAddress() {
        return street + ", " + number + " - " + neighbourhood + ", " + city + "/" + state;
    }

}
