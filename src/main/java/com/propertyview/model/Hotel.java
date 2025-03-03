package com.propertyview.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String brand;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
        CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
        CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "contacts_id")
    private ContactInfo contacts;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "checkIn", column = @Column(nullable = true)),
        @AttributeOverride(name = "checkOut", column = @Column(nullable = true))
    })
    private ArrivalTime arrivalTime;

    @OneToMany(mappedBy = "hotel", cascade = {CascadeType.PERSIST,
        CascadeType.MERGE}, orphanRemoval = true)
    private Set<Amenity> amenities = new HashSet<>();

    public ArrivalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(ArrivalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactInfo getContacts() {
        return contacts;
    }

    public void setContacts(ContactInfo contactInfo) {
        this.contacts = contactInfo;
    }

    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Hotel hotel = (Hotel) o;
        return Objects.equals(id, hotel.id) &&
               Objects.equals(name, hotel.name) &&
               Objects.equals(brand, hotel.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand);
    }

    @Override
    public String toString() {
        return "Hotel{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", brand='" + brand + '\'' +
               '}';
    }
}
