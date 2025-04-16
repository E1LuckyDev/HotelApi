package com.propertyview.propertyservice.repository;

import com.propertyview.propertyservice.model.Amenity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

    @Query("""
        SELECT am.amenity AS amenity, COUNT(am) AS count
        FROM Amenity am
        GROUP BY am.amenity
        """)
    List<Object[]> countByAmenity();
}
