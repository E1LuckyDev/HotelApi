package com.propertyview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class HotelDto {

    @Schema(description = "Hotel ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "Hotel name", example = "DoubleTree by Hilton Minsk", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Schema(description = "Hotel description", example = "Luxury hotel in Minsk...")
    private String description;

    @Schema(description = "Hotel brand", example = "Hilton", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Brand cannot be blank")
    private String brand;

    @Schema(description = "Hotel address", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Address cannot be null")
    private AddressDto address;

    @Schema(description = "Contact information", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Contacts cannot be null")
    private ContactInfoDto contacts;

    @Schema(description = "Check-in and check-out times", requiredMode = Schema.RequiredMode.REQUIRED)
    ArrivalTimeDto arrivalTime;

    @Schema(description = "List of amenities", example = "[\"Free WiFi\", \"Free Parking\"]")
    private List<String> amenities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public ContactInfoDto getContacts() {
        return contacts;
    }

    public void setContacts(ContactInfoDto contactInfoDto) {
        this.contacts = contactInfoDto;
    }

    public ArrivalTimeDto getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ArrivalTimeDto arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }
}
