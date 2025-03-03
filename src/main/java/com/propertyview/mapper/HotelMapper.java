package com.propertyview.mapper;

import com.propertyview.dto.HotelDto;
import com.propertyview.dto.request.CreateHotelRequestDto;
import com.propertyview.dto.response.ShortHotelDto;
import com.propertyview.model.Address;
import com.propertyview.model.Amenity;
import com.propertyview.model.ArrivalTime;
import com.propertyview.model.ContactInfo;
import com.propertyview.model.Hotel;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    private final AddressMapper addressMapper;

    private final ContactInfoMapper contactInfoMapper;

    private final ArrivalTimeMapper arrivalTimeMapper;

    public HotelMapper(AddressMapper addressMapper, ContactInfoMapper contactInfoMapper,
        ArrivalTimeMapper arrivalTimeMapper) {
        this.addressMapper = addressMapper;
        this.contactInfoMapper = contactInfoMapper;
        this.arrivalTimeMapper = arrivalTimeMapper;
    }

    public ShortHotelDto toShortDto(Hotel hotel) {
        ShortHotelDto dto = new ShortHotelDto();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setDescription(hotel.getDescription());

        dto.setAddress(addressMapper.addressToString(hotel.getAddress()));

        if (hotel.getContacts() != null) {
            dto.setPhone(hotel.getContacts().getPhone());
        }

        return dto;
    }

    public HotelDto toDto(Hotel hotel) {
        HotelDto dto = new HotelDto();
        dto.setId(hotel.getId());
        dto.setName(hotel.getName());
        dto.setDescription(hotel.getDescription());
        dto.setBrand(hotel.getBrand());

        dto.setAddress(addressMapper.toDto(hotel.getAddress()));

        dto.setContacts(contactInfoMapper.toDto(hotel.getContacts()));

        if (hotel.getArrivalTime() != null) {
            dto.setArrivalTime(arrivalTimeMapper.toDto(hotel.getArrivalTime()));
        }

        if (hotel.getAmenities() != null) {
            dto.setAmenities(hotel.getAmenities().stream()
                .map(Amenity::getAmenity)
                .collect(Collectors.toList()));
        }

        return dto;
    }

    public Hotel toEntity(CreateHotelRequestDto request) {
        if (request == null) {
            return null;
        }

        Hotel hotel = new Hotel();
        hotel.setName(request.getName());
        hotel.setBrand(request.getBrand());
        hotel.setDescription(request.getDescription());

        if (request.getAddress() != null) {
            Address address = addressMapper.toEntity(request.getAddress());
            hotel.setAddress(address);
        }

        if (request.getContacts() != null) {
            ContactInfo contactInfo = contactInfoMapper.toEntity(request.getContacts());
            hotel.setContacts(contactInfo);
        }

        if (request.getArrivalTime() != null) {
            ArrivalTime arrivalTime = arrivalTimeMapper.toEntity(request.getArrivalTime());
            hotel.setArrivalTime(arrivalTime);
        }

        if (request.getAmenities() != null && !request.getAmenities().isEmpty()) {
            Set<Amenity> amenities = request.getAmenities().stream()
                .map(amenityName -> {
                    Amenity amenity = new Amenity();
                    amenity.setAmenity(amenityName);
                    return amenity;
                })
                .collect(Collectors.toSet());
            hotel.setAmenities(amenities);
        }

        return hotel;
    }
}
