package com.propertyview.propertyservice.controller;

import com.propertyview.propertyservice.dto.HotelDto;
import com.propertyview.propertyservice.dto.request.CreateHotelRequestDto;
import com.propertyview.propertyservice.dto.response.ShortHotelDto;
import com.propertyview.propertyservice.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
@Tag(name = "Hotel Management", description = "Endpoints for managing hotels")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Operation(summary = "Get all hotels with short information")
    @ApiResponse(responseCode = "200", description = "List of hotels", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShortHotelDto.class)))
    @GetMapping
    public List<ShortHotelDto> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @Operation(summary = "Get detailed information about a specific hotel by ID")
    @ApiResponse(responseCode = "200", description = "Detailed hotel information", content = @Content(mediaType = "application/json", schema = @Schema(implementation = HotelDto.class)))
    @ApiResponse(responseCode = "404", description = "Hotel not found")
    @GetMapping("/{id}")
    public HotelDto getHotelById(@PathVariable Long id) {
        return hotelService.getHotelById(id);
    }

    @Operation(summary = "Create a new hotel")
    @ApiResponse(responseCode = "201", description = "Hotel created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShortHotelDto.class)))
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @PostMapping
    public ResponseEntity<ShortHotelDto> createHotel(@Valid @RequestBody CreateHotelRequestDto request) {
        ShortHotelDto createdHotel = hotelService.createHotel(request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(createdHotel);
    }

    @Operation(summary = "Add amenities to a specific hotel by ID")
    @ApiResponse(responseCode = "200", description = "Amenities added", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShortHotelDto.class)))
    @ApiResponse(responseCode = "404", description = "Hotel not found")
    @PostMapping("/{id}/amenities")
    @ResponseStatus(HttpStatus.CREATED)
    public HotelDto addAmenities(@PathVariable Long id, @RequestBody List<String> amenities) {
        return hotelService.addAmenities(id, amenities);
    }
}
