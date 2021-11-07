package com.ebious.aviasale.service;


import com.ebious.aviasale.domain.entity.Bookings;
import com.ebious.aviasale.domain.entity.Users;
import com.ebious.aviasale.domain.pojo.Price;
import com.ebious.aviasale.expection.BookingNotFoundException;
import com.ebious.aviasale.repository.BookingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BookingsService {
    private final BookingsRepository bookingsRepository;

    @Autowired
    public BookingsService(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    public Bookings createBooking(Users user, List<Price> prices, Integer numberOfTickets) {
        Bookings booking = new Bookings();
        booking.setBookDate(OffsetDateTime.now());
        booking.setBookRef("B" + UUID.randomUUID().toString().substring(0, 5).toUpperCase());
        BigDecimal totalPrice = prices.stream()
                .map(p -> p.getValue().multiply(BigDecimal.valueOf(numberOfTickets)))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        booking.setTotalAmount(totalPrice);
        booking.setUserProfile(user.getUserProfile());
        bookingsRepository.save(booking);
        return booking;
    }

    public void deleteBooking(Bookings booking) {
        bookingsRepository.delete(booking);
    }

    public Bookings getBookingByBookRef(String bookRef) throws BookingNotFoundException {
        return bookingsRepository.findByBookRef(bookRef)
                .orElseThrow(() -> new BookingNotFoundException("Booking not Found by " + bookRef, HttpStatus.NOT_FOUND));
    }

    public List<Bookings> getBookingsByUser(Users user) {
        return bookingsRepository.findAllByUser(user.getId());
    }
}
