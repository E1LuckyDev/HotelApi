package com.propertyview.propertyservice.mapper;

import com.propertyview.propertyservice.dto.AddressDto;
import com.propertyview.propertyservice.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public String addressToString(Address address) {
        if (address == null) {
            return null;
        }
        return String.format("%d %s, %s, %s, %s",
            address.getHouseNumber(),
            address.getStreet(),
            address.getCity(),
            address.getPostCode(),
            address.getCountry());
    }

    public AddressDto toDto(Address address) {
        if (address == null) {
            return null;
        }
        AddressDto dto = new AddressDto();
        dto.setHouseNumber(address.getHouseNumber());
        dto.setStreet(address.getStreet());
        dto.setCity(address.getCity());
        dto.setCountry(address.getCountry());
        dto.setPostCode(address.getPostCode());
        return dto;
    }

    public Address toEntity(AddressDto dto) {
        if (dto == null) {
            return null;
        }
        Address entity = new Address();
        entity.setHouseNumber(dto.getHouseNumber());
        entity.setStreet(dto.getStreet());
        entity.setCity(dto.getCity());
        entity.setCountry(dto.getCountry());
        entity.setPostCode(dto.getPostCode());
        return entity;
    }
}
