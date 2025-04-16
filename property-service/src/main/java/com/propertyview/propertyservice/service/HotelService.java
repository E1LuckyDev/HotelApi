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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final HotelMapper hotelMapper;

    @Autowired
    public HotelService(
        HotelRepository hotelRepository,
        AmenityRepository amenityRepository,
        HotelMapper hotelMapper
    ) {
        this.hotelRepository = hotelRepository;
        this.amenityRepository = amenityRepository;
        this.hotelMapper = hotelMapper;
    }

    public List<ShortHotelDto> getAllHotels() {
        return hotelRepository.findAll().stream()
            .map(hotelMapper::toShortDto)
            .collect(Collectors.toList());
    }

    public HotelDto getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Hotel with ID " + id + " not found"));
        return hotelMapper.toDto(hotel);
    }

    public List<ShortHotelDto> searchHotels(String name, String brand, String city, String country,
        List<String> amenities) {

        if (amenities != null && amenities.isEmpty()) {
            amenities = null;
        }

        if (amenities != null) {
            amenities = amenities.stream()
                .map(String::toLowerCase) // Преобразуем каждый элемент в нижний регистр
                .collect(Collectors.toList());
        }

        List<Hotel> hotels = hotelRepository.searchHotels(name, brand, city, country, amenities);

        return hotels.stream()
            .map(hotelMapper::toShortDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public ShortHotelDto createHotel(CreateHotelRequestDto request) {
        Hotel hotel = hotelMapper.toEntity(request);

        Hotel savedHotel = hotelRepository.save(hotel);

        return hotelMapper.toShortDto(savedHotel);
    }

    @Transactional
    public HotelDto addAmenities(Long id, List<String> amenities) {
        Hotel hotel = hotelRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Hotel with ID " + id + " not found"));
        Set<Amenity> existingAmenities = hotel.getAmenities();

        Set<Amenity> newAmenities = amenities.stream()
            .map(String::trim)
            .filter(amenityName -> existingAmenities.stream()
                .noneMatch(amenity -> amenity.getAmenity().equalsIgnoreCase(amenityName)))
            .map(amenityName -> {
                Amenity amenity = new Amenity();
                amenity.setAmenity(amenityName);
                amenity.setHotel(hotel);
                return amenity;
            })
            .collect(Collectors.toSet());

        if (!newAmenities.isEmpty()){
            hotel.getAmenities().addAll(newAmenities);

            hotelRepository.save(hotel);
        }

        return hotelMapper.toDto(hotel);
    }

    @Transactional(readOnly = true)
    public Map<String, Integer> getHistogram(String param) {
        return switch (param.toLowerCase()) {
            case "brand" -> buildHistogram(hotelRepository.countByBrand());
            case "city" -> buildHistogram(hotelRepository.countByCity());
            case "country" -> buildHistogram(hotelRepository.countByCountry());
            case "amenities" -> buildHistogram(amenityRepository.countByAmenity());
            default -> throw new IllegalArgumentException("Invalid parameter: " + param);
        };
    }

    private Map<String, Integer> buildHistogram(List<Object[]> results) {
        Map<String, Integer> histogram = new HashMap<>();
        for (Object[] result : results) {
            String key = (String) result[0];
            Integer count = ((Number) result[1]).intValue(); // Преобразуем в Integer
            histogram.put(key, count);
        }
        return histogram;
    }
}
