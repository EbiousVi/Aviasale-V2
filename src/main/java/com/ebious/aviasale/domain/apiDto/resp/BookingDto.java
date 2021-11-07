package com.ebious.aviasale.domain.apiDto.resp;

import com.ebious.aviasale.domain.entity.Bookings;

import java.util.List;

public class BookingDto {
    private Bookings booking;
    private List<BookingPartDto> bookingPartDtos;

    public BookingDto() {
    }

    public Bookings getBooking() {
        return booking;
    }

    public void setBooking(Bookings booking) {
        this.booking = booking;
    }

    public List<BookingPartDto> getBookingPartDtos() {
        return bookingPartDtos;
    }

    public void setBookingPartDtos(List<BookingPartDto> bookingPartDtos) {
        this.bookingPartDtos = bookingPartDtos;
    }

    @Override
    public String toString() {
        return "BookingDto{" +
                "booking=" + booking +
                ", bookingPartDtos=" + bookingPartDtos +
                '}';
    }
}
