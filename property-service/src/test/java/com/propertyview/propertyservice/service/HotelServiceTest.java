package com.propertyview.propertyservice.service;

import com.propertyview.propertyservice.dto.HotelDto;
import com.propertyview.propertyservice.dto.request.CreateHotelRequestDto;
import com.propertyview.propertyservice.dto.response.ShortHotelDto;
import com.propertyview.propertyservice.exception.EntityNotFoundException;
import com.propertyview.propertyservice.mapper.HotelMapper;
import com.propertyview.propertyservice.model.Amenity;
import com.propertyview.propertyservice.model.Hotel;
import com.propertyview.propertyservice.repository.AmenityRepository;
import com.propertyview.propertyservice.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class HotelServiceTest {

    private final Long hotelId = 1L;
    private HotelRepository hotelRepository;
    private AmenityRepository amenityRepository;
    private HotelMapper hotelMapper;
    private HotelService hotelService;
    private Hotel hotel1, hotel2;

    @BeforeEach
    void setUp() {
        hotelRepository = mock(HotelRepository.class);
        amenityRepository = mock(AmenityRepository.class);
        hotelMapper = mock(HotelMapper.class);
        hotelService = new HotelService(hotelRepository, amenityRepository, hotelMapper);

        hotel1 = new Hotel();
        hotel2 = new Hotel();
        hotel1.setId(1L);
        hotel2.setId(2L);
        hotel1.setName("DoubleTree by Hilton Minsk");
        hotel2.setName("Radisson Blu Minsk");

        Amenity amenity1 = new Amenity();
        amenity1.setAmenity("Free WiFi");
        amenity1.setHotel(hotel1);

        Amenity amenity2 = new Amenity();
        amenity2.setAmenity("Free Parking");
        amenity2.setHotel(hotel1);

        Set<Amenity> existingAmenities = new HashSet<>(Arrays.asList(amenity1, amenity2));
        hotel1.setAmenities(existingAmenities);
    }

    @Test
    void testGetAllHotels() {

        List<Hotel> hotels = Arrays.asList(hotel1, hotel2);

        when(hotelRepository.findAll()).thenReturn(hotels);

        ShortHotelDto shortHotelDto1 = new ShortHotelDto();
        shortHotelDto1.setId(1L);
        shortHotelDto1.setName("DoubleTree by Hilton Minsk");

        ShortHotelDto shortHotelDto2 = new ShortHotelDto();
        shortHotelDto2.setId(2L);
        shortHotelDto2.setName("Radisson Blu Minsk");

        when(hotelMapper.toShortDto(hotel1)).thenReturn(shortHotelDto1);
        when(hotelMapper.toShortDto(hotel2)).thenReturn(shortHotelDto2);

        List<ShortHotelDto> result = hotelService.getAllHotels();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("DoubleTree by Hilton Minsk", result.get(0).getName());
        assertEquals("Radisson Blu Minsk", result.get(1).getName());

        verify(hotelRepository, times(1)).findAll();
        verify(hotelMapper, times(2)).toShortDto(any(Hotel.class));
    }

    @Test
    void testGetHotelById_Success() {

        HotelDto hotelDto = new HotelDto();
        hotelDto.setId(1L);
        hotelDto.setName("DoubleTree by Hilton Minsk");

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel1));

        when(hotelMapper.toDto(hotel1)).thenReturn(hotelDto);

        HotelDto result = hotelService.getHotelById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("DoubleTree by Hilton Minsk", result.getName());

        verify(hotelRepository, times(1)).findById(1L);
        verify(hotelMapper, times(1)).toDto(hotel1);
    }

    @Test
    void testGetHotelById_Failure() {
        when(hotelRepository.findById(999L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            hotelService.getHotelById(999L);
        });

        assertEquals("Hotel with ID 999 not found", exception.getMessage());

        verify(hotelRepository, times(1)).findById(999L);
        verify(hotelMapper, never()).toDto(any(Hotel.class));
    }

    @Test
    void testCreateHotel() {
        CreateHotelRequestDto request = new CreateHotelRequestDto();
        request.setName("DoubleTree by Hilton Minsk");

        ShortHotelDto shortHotelDto = new ShortHotelDto();
        shortHotelDto.setId(1L);
        shortHotelDto.setName("DoubleTree by Hilton Minsk");

        when(hotelMapper.toEntity(request)).thenReturn(hotel1);

        when(hotelRepository.save(hotel1)).thenReturn(hotel1);

        when(hotelMapper.toShortDto(hotel1)).thenReturn(shortHotelDto);

        ShortHotelDto result = hotelService.createHotel(request);

        assertNotNull(result);
        assertEquals(hotelId, result.getId());
        assertEquals("DoubleTree by Hilton Minsk", result.getName());

        verify(hotelMapper, times(1)).toEntity(request);
        verify(hotelRepository, times(1)).save(hotel1);
        verify(hotelMapper, times(1)).toShortDto(hotel1);
    }

    @Test
    void testAddAmenities_Success() {
        List<String> amenitiesToAdd = Arrays.asList("Gym", "Pool");

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel1));

        HotelDto updatedHotelDto = new HotelDto();
        updatedHotelDto.setId(hotelId);
        updatedHotelDto.setName("DoubleTree by Hilton Minsk");
        updatedHotelDto.setAmenities(Arrays.asList("Free WiFi", "Free Parking", "Gym", "Pool"));

        when(hotelMapper.toDto(hotel1)).thenReturn(updatedHotelDto);

        HotelDto result = hotelService.addAmenities(hotelId, amenitiesToAdd);

        assertNotNull(result);
        assertEquals(hotelId, result.getId());
        assertEquals("DoubleTree by Hilton Minsk", result.getName());
        assertEquals(4, result.getAmenities().size());
        assertTrue(result.getAmenities()
            .containsAll(Arrays.asList("Free WiFi", "Free Parking", "Gym", "Pool")));

        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelMapper, times(1)).toDto(hotel1);
    }

    @Test
    void testAddAmenities_DuplicatesIgnored() {
        List<String> amenitiesToAdd = Arrays.asList("free wifi", "Free Parking");

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel1));

        HotelDto updatedHotelDto = new HotelDto();
        updatedHotelDto.setId(hotelId);
        updatedHotelDto.setName("DoubleTree by Hilton Minsk");
        updatedHotelDto.setAmenities(Arrays.asList("Free WiFi", "Free Parking"));

        when(hotelMapper.toDto(hotel1)).thenReturn(updatedHotelDto);

        HotelDto result = hotelService.addAmenities(hotelId, amenitiesToAdd);

        assertNotNull(result);
        assertEquals(hotelId, result.getId());
        assertEquals("DoubleTree by Hilton Minsk", result.getName());
        assertEquals(2, result.getAmenities().size());
        assertTrue(result.getAmenities().containsAll(Arrays.asList("Free WiFi", "Free Parking")));

        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelMapper, times(1)).toDto(hotel1);
    }

    @Test
    void testAddAmenities_HotelNotFound() {
        Long hotelId = 999L;
        List<String> amenitiesToAdd = List.of("Gym");

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            hotelService.addAmenities(hotelId, amenitiesToAdd);
        });

        assertEquals("Hotel with ID 999 not found", exception.getMessage());

        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelMapper, never()).toDto(any(Hotel.class));
    }

    @Test
    void testAddAmenities_EmptyList() {
        List<String> amenitiesToAdd = Collections.emptyList();

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel1));

        HotelDto updatedHotelDto = new HotelDto();
        updatedHotelDto.setId(hotelId);
        updatedHotelDto.setName("DoubleTree by Hilton Minsk");
        updatedHotelDto.setAmenities(Arrays.asList("Free WiFi", "Free Parking"));

        when(hotelMapper.toDto(hotel1)).thenReturn(updatedHotelDto);

        HotelDto result = hotelService.addAmenities(hotelId, amenitiesToAdd);

        assertNotNull(result);
        assertEquals(hotelId, result.getId());
        assertEquals("DoubleTree by Hilton Minsk", result.getName());
        assertEquals(2, result.getAmenities().size());
        assertTrue(result.getAmenities().containsAll(Arrays.asList("Free WiFi", "Free Parking")));

        // Проверяем вызовы моков
        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelMapper, times(1)).toDto(hotel1);
    }

    @Test
    void testAddAmenities_AllDuplicates() {
        List<String> amenitiesToAdd = Arrays.asList("free wifi", "FREE PARKING");

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel1));

        HotelDto updatedHotelDto = new HotelDto();
        updatedHotelDto.setId(hotelId);
        updatedHotelDto.setName("DoubleTree by Hilton Minsk");
        updatedHotelDto.setAmenities(Arrays.asList("Free WiFi", "Free Parking"));

        when(hotelMapper.toDto(hotel1)).thenReturn(updatedHotelDto);

        HotelDto result = hotelService.addAmenities(hotelId, amenitiesToAdd);

        assertNotNull(result);
        assertEquals(hotelId, result.getId());
        assertEquals("DoubleTree by Hilton Minsk", result.getName());
        assertEquals(2, result.getAmenities().size());
        assertTrue(result.getAmenities().containsAll(Arrays.asList("Free WiFi", "Free Parking")));

        verify(hotelRepository, times(1)).findById(hotelId);
        verify(hotelMapper, times(1)).toDto(hotel1);
    }


    @Test
    void testSearchHotels() {
        when(hotelRepository.searchHotels("DoubleTree by Hilton Minsk", null, null, null, null))
            .thenReturn(List.of(hotel1));

        ShortHotelDto shortHotelDto1 = new ShortHotelDto();
        shortHotelDto1.setId(1L);
        shortHotelDto1.setName("DoubleTree by Hilton Minsk");

        ShortHotelDto shortHotelDto2 = new ShortHotelDto();
        shortHotelDto2.setId(2L);
        shortHotelDto2.setName("Radisson Blu Minsk");

        when(hotelMapper.toShortDto(hotel1)).thenReturn(shortHotelDto1);
        when(hotelMapper.toShortDto(hotel2)).thenReturn(shortHotelDto2);

        List<ShortHotelDto> result = hotelService.searchHotels("DoubleTree by Hilton Minsk", null,
            null, null, null);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("DoubleTree by Hilton Minsk", result.get(0).getName());

        verify(hotelRepository, times(1)).searchHotels("DoubleTree by Hilton Minsk", null, null,
            null, null);
        verify(hotelMapper, times(1)).toShortDto(any(Hotel.class));
    }

    @Test
    void testGetHistogram_Success() {
        List<Object[]> brandResults = Arrays.asList(
            new Object[]{"Hilton", 2L},
            new Object[]{"Marriott", 1L}
        );

        when(hotelRepository.countByBrand()).thenReturn(brandResults);

        Map<String, Integer> result = hotelService.getHistogram("brand");

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(2, result.get("Hilton").intValue());
        assertEquals(1, result.get("Marriott").intValue());

        verify(hotelRepository, times(1)).countByBrand();
    }

    @Test
    void testGetHistogram_InvalidParameter() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            hotelService.getHistogram("invalid_param");
        });

        assertEquals("Invalid parameter: invalid_param", exception.getMessage());

        verify(hotelRepository, never()).countByBrand();
        verify(hotelRepository, never()).countByCity();
        verify(hotelRepository, never()).countByCountry();
        verify(amenityRepository, never()).countByAmenity();
    }
}
