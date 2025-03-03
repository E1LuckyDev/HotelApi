package com.propertyview.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AddressDto {

    @Schema(description = "House number", example = "9", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "House number cannot be null")
    private Integer houseNumber;

    @Schema(description = "Street name", example = "Pobediteley Avenue", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Street cannot be blank")
    private String street;

    @Schema(description = "City name", example = "Minsk", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "City cannot be blank")
    private String city;

    @Schema(description = "Country name", example = "Belarus", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Country cannot be blank")
    private String country;

    @Schema(description = "Postal code", example = "220004")
    @NotBlank(message = "PostCode cannot be blank")
    private String postCode;

    public Integer getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(Integer houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }
}
