package com.propertyview.propertyservice.dto.request;

import com.propertyview.propertyservice.dto.AddressDto;
import com.propertyview.propertyservice.dto.ArrivalTimeDto;
import com.propertyview.propertyservice.dto.ContactInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;

public class CreateHotelRequestDto {

    @Schema(description = "Name of the hotel", example = "DoubleTree by Hilton Minsk", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Schema(description = "Description of the hotel", example = "Luxury hotel in Minsk...")
    private String description;

    @Schema(description = "Brand of the hotel", example = "Hilton", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Brand cannot be blank")
    private String brand;

    @Schema(description = "Address of the hotel", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Address cannot be null")
    @Valid
    private AddressDto address;

    @Schema(description = "Contact information of the hotel", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Contacts cannot be null")
    @Valid
    private ContactInfoDto contacts;

    @Schema(description = "Check-in and check-out times", requiredMode = Schema.RequiredMode.REQUIRED)
    ArrivalTimeDto arrivalTime;

    @Schema(description = "List of amenities", example = "[\"Free WiFi\", \"Free Parking\"]")
    private Set<String> amenities;

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

    public void setContacts(ContactInfoDto contacts) {
        this.contacts = contacts;
    }

    public ArrivalTimeDto getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ArrivalTimeDto arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Set<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<String> amenities) {
        this.amenities = amenities;
    }
}
