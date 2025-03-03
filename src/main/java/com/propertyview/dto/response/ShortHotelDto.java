package com.propertyview.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class ShortHotelDto {

    @Schema(description = "Hotel ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "Hotel name", example = "DoubleTree by Hilton Minsk", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Hotel description", example = "Luxury hotel in Minsk...")
    private String description;

    @Schema(description = "Hotel address as a string", example = "9 Pobediteley Avenue, Minsk, 220004, Belarus", requiredMode = Schema.RequiredMode.REQUIRED)
    private String address;

    @Schema(description = "Phone number", example = "+375 17 309-80-00", requiredMode = Schema.RequiredMode.REQUIRED)
    private String phone;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
