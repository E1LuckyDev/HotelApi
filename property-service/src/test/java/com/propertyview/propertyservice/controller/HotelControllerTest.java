package com.propertyview.propertyservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.propertyview.propertyservice.dto.AddressDto;
import com.propertyview.propertyservice.dto.ContactInfoDto;
import com.propertyview.propertyservice.dto.HotelDto;
import com.propertyview.propertyservice.dto.request.CreateHotelRequestDto;
import com.propertyview.propertyservice.dto.response.ShortHotelDto;
import com.propertyview.propertyservice.exception.EntityNotFoundException;
import com.propertyview.propertyservice.service.HotelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    private ShortHotelDto shortHotelDto1;
    private ShortHotelDto shortHotelDto2;
    private HotelDto hotelDto;

    @BeforeEach
    void setUp() {
        shortHotelDto1 = new ShortHotelDto();
        shortHotelDto1.setId(1L);
        shortHotelDto1.setName("DoubleTree by Hilton Minsk");

        shortHotelDto2 = new ShortHotelDto();
        shortHotelDto2.setId(2L);
        shortHotelDto2.setName("Radisson Blu Minsk");

        hotelDto = new HotelDto();
        hotelDto.setId(1L);
        hotelDto.setName("DoubleTree by Hilton Minsk");
        hotelDto.setBrand("Hilton");
    }

    @Test
    void testGetAllHotels_Success() throws Exception {
        List<ShortHotelDto> hotels = Arrays.asList(shortHotelDto1, shortHotelDto2);
        when(hotelService.getAllHotels()).thenReturn(hotels);

        mockMvc.perform(get("/hotels")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1L))
            .andExpect(jsonPath("$[0].name").value("DoubleTree by Hilton Minsk"))
            .andExpect(jsonPath("$[1].id").value(2L))
            .andExpect(jsonPath("$[1].name").value("Radisson Blu Minsk"));

        verify(hotelService, times(1)).getAllHotels();
    }

    @Test
    void testGetHotelById_Success() throws Exception {
        when(hotelService.getHotelById(1L)).thenReturn(hotelDto);

        mockMvc.perform(get("/hotels/1")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("DoubleTree by Hilton Minsk"))
            .andExpect(jsonPath("$.brand").value("Hilton"));

        verify(hotelService, times(1)).getHotelById(1L);
    }

    @Test
    void testGetHotelById_NotFound() throws Exception {
        when(hotelService.getHotelById(999L)).thenThrow(
            new EntityNotFoundException("Hotel with ID 999 not found"));

        mockMvc.perform(get("/hotels/999")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(hotelService, times(1)).getHotelById(999L);
    }

    @Test
    void testCreateHotel_Success() throws Exception {
        CreateHotelRequestDto request = getCreateHotelRequestDto();
        request.setArrivalTime(null);

        ShortHotelDto createdHotel = new ShortHotelDto();
        createdHotel.setId(1L);
        createdHotel.setName("New Hotel");

        when(hotelService.createHotel(any(CreateHotelRequestDto.class))).thenReturn(createdHotel);

        mockMvc.perform(post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(hotelService, times(1)).createHotel(any(CreateHotelRequestDto.class));
    }

    @Test
    void testCreateHotel_InvalidData() throws Exception {
        CreateHotelRequestDto invalidRequest = new CreateHotelRequestDto();
        invalidRequest.setName("");
        invalidRequest.setBrand("Hilton");

        mockMvc.perform(post("/hotels")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(invalidRequest)))
            .andExpect(status().isBadRequest());

        verify(hotelService, never()).createHotel(any(CreateHotelRequestDto.class));
    }

    @Test
    void testAddAmenities_Success() throws Exception {
        HotelDto updatedHotel = new HotelDto();
        updatedHotel.setId(1L);
        updatedHotel.setName("DoubleTree by Hilton Minsk");

        when(hotelService.addAmenities(1L, Arrays.asList("Free WiFi", "Free Parking"))).thenReturn(
            updatedHotel);

        mockMvc.perform(post("/hotels/1/amenities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(Arrays.asList("Free WiFi", "Free Parking"))))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(
                jsonPath("$.name").value("DoubleTree by Hilton Minsk"));

        verify(hotelService, times(1)).addAmenities(1L, Arrays.asList("Free WiFi", "Free Parking"));
    }

    @Test
    void testAddAmenities_HotelNotFound() throws Exception {
        when(hotelService.addAmenities(999L, List.of("Free WiFi"))).thenThrow(
            new EntityNotFoundException("Hotel with ID 999 not found"));

        mockMvc.perform(post("/hotels/999/amenities")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(List.of("Free WiFi"))))
            .andExpect(status().isNotFound());

        verify(hotelService, times(1)).addAmenities(999L, List.of("Free WiFi"));
    }

    private static CreateHotelRequestDto getCreateHotelRequestDto() {
        CreateHotelRequestDto request = new CreateHotelRequestDto();
        request.setName("New Hotel");
        request.setBrand("Hilton");

        AddressDto addressDto = new AddressDto();
        addressDto.setHouseNumber(9);
        addressDto.setStreet("Test Street");
        addressDto.setCity("Test City");
        addressDto.setCountry("TestCountry");
        addressDto.setPostCode("12345");
        request.setAddress(addressDto);

        ContactInfoDto contactInfoDto = new ContactInfoDto();
        contactInfoDto.setPhone("+375 17 309-80-00");
        contactInfoDto.setEmail("info@test.com");
        request.setContacts(contactInfoDto);
        request.setAmenities(new HashSet<>());
        return request;
    }

    private static String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
