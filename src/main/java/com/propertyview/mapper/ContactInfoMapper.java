package com.propertyview.mapper;

import com.propertyview.dto.ContactInfoDto;
import com.propertyview.model.ContactInfo;
import org.springframework.stereotype.Component;

@Component
public class ContactInfoMapper {

    public ContactInfoDto toDto(ContactInfo contactInfo) {
        if (contactInfo == null) {
            return null;
        }
        ContactInfoDto dto = new ContactInfoDto();
        dto.setPhone(contactInfo.getPhone());
        dto.setEmail(contactInfo.getEmail());
        return dto;
    }

    public ContactInfo toEntity(ContactInfoDto dto) {
        if (dto == null) {
            return null;
        }
        ContactInfo entity = new ContactInfo();
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        return entity;
    }
}
