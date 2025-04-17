package com.propertyview.propertyservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import java.util.Objects;

@Embeddable
public class ArrivalTime {

    @Column(nullable = false)
    private LocalTime checkIn;

    @Column(nullable = false)
    private LocalTime checkOut;

    public LocalTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalTime checkIn) {
        this.checkIn = checkIn;
    }

    public LocalTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalTime checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArrivalTime that = (ArrivalTime) o;
        return Objects.equals(checkIn, that.checkIn) &&
               Objects.equals(checkOut, that.checkOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checkIn, checkOut); // Учитываем все поля
    }

    @Override
    public String toString() {
        return "ArrivalTime{" +
               "checkIn='" + checkIn + '\'' +
               ", checkOut='" + checkOut + '\'' +
               '}';
    }
}
