package com.propertyview.propertyservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class ContactInfoDto {

    @Schema(description = "Phone number", example = "+375 17 309-80-00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Phone cannot be blank")
    private String phone;

    @Schema(description = "Email address", example = "info@hilton.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email cannot be blank")
    private String email;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
