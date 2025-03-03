package com.propertyview.controller;

import com.propertyview.dto.response.ShortHotelDto;
import com.propertyview.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@Tag(name = "Hotel Search", description = "Endpoints for searching hotels")
public class SearchController {

    private final HotelService hotelService;

    @Autowired
    public SearchController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Operation(summary = "Search hotels by parameters")
    @ApiResponse(responseCode = "200", description = "List of matching hotels", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShortHotelDto.class)))
    @GetMapping
    public List<ShortHotelDto> searchHotels(
        @Parameter(description = "Hotel name", example = "DoubleTree by Hilton Minsk") @RequestParam(required = false) String name,
        @Parameter(description = "Hotel brand", example = "Hilton") @RequestParam(required = false) String brand,
        @Parameter(description = "City of the hotel", example = "Minsk") @RequestParam(required = false) String city,
        @Parameter(description = "Country of the hotel", example = "Belarus") @RequestParam(required = false) String country,
        @Parameter(description = "List of amenities", example = "[\"Free WiFi\", \"Free Parking\"]") @RequestParam(required = false) List<String> amenities
    ) {
        return hotelService.searchHotels(name, brand, city, country, amenities);
    }
}
