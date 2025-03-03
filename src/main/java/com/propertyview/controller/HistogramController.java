package com.propertyview.controller;

import com.propertyview.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/histogram")
@Tag(name = "Hotel Histogram", description = "Endpoints for generating hotel histograms")
public class HistogramController {
    private final HotelService hotelService;

    @Autowired
    public HistogramController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Operation(summary = "Generate histogram by parameter")
    @ApiResponse(responseCode = "200", description = "Histogram data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))
    @ApiResponse(responseCode = "400", description = "Invalid parameter")
    @GetMapping("/{param}")
    public Map<String, Integer> getHistogram(@Parameter(description = "Parameter for histogram (brand, city, country, amenities)", example = "city") @PathVariable String param) {
        return hotelService.getHistogram(param);
    }
}
