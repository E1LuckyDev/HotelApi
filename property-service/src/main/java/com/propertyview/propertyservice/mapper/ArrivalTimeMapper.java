package com.propertyview.propertyservice.mapper;

import com.propertyview.propertyservice.dto.ArrivalTimeDto;
import com.propertyview.propertyservice.model.ArrivalTime;
import org.springframework.stereotype.Component;

@Component
public class ArrivalTimeMapper {
    public ArrivalTime toEntity(ArrivalTimeDto dto) {
        if (dto == null) {
            return null;
        }
        ArrivalTime arrivalTime = new ArrivalTime();
        arrivalTime.setCheckIn(dto.getCheckIn());
        arrivalTime.setCheckOut(dto.getCheckOut());
        return arrivalTime;
    }

    public ArrivalTimeDto toDto(ArrivalTime entity) {
        if (entity == null) {
            return null;
        }
        ArrivalTimeDto dto = new ArrivalTimeDto();
        dto.setCheckIn(entity.getCheckIn());
        dto.setCheckOut(entity.getCheckOut());
        return dto;
    }
}
