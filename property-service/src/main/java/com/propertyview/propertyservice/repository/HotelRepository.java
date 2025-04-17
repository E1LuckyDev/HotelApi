package com.propertyview.propertyservice.repository;

import com.propertyview.propertyservice.model.Hotel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    @Query("""
        SELECT h FROM Hotel h
        LEFT JOIN FETCH h.address a
        WHERE (:name IS NULL OR LOWER(h.name) = LOWER(:name))
          AND (:brand IS NULL OR LOWER(h.brand) = LOWER(:brand))
          AND (:city IS NULL OR LOWER(a.city) = LOWER(:city))
          AND (:country IS NULL OR LOWER(a.country) = LOWER(:country))
          AND (:amenities IS NULL OR h.id IN (
              SELECT am.hotel.id FROM Amenity am WHERE LOWER(am.amenity) IN :amenities
          ))
        """)
    List<Hotel> searchHotels(
        @Param("name") String name,
        @Param("brand") String brand,
        @Param("city") String city,
        @Param("country") String country,
        @Param("amenities") List<String> amenities
    );

    // Подсчет отелей по brand
    @Query("""
        SELECT h.brand AS brand, COUNT(h) AS count
        FROM Hotel h
        GROUP BY h.brand
        """)
    List<Object[]> countByBrand();

    // Подсчет отелей по city
    @Query("""
        SELECT a.city AS city, COUNT(h) AS count
        FROM Hotel h
        LEFT JOIN h.address a
        GROUP BY a.city
        """)
    List<Object[]> countByCity();

    // Подсчет отелей по country
    @Query("""
        SELECT a.country AS country, COUNT(h) AS count
        FROM Hotel h
        LEFT JOIN h.address a
        GROUP BY a.country
        """)
    List<Object[]> countByCountry();
}
