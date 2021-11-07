package com.ebious.aviasale.controller.booking;

import com.ebious.aviasale.domain.apiDto.req.PreBookingDto;
import com.ebious.aviasale.domain.apiDto.resp.BookingDto;

import com.ebious.aviasale.domain.entity.Users;
import com.ebious.aviasale.expection.*;
import com.ebious.aviasale.service.BookingsService;
import com.ebious.aviasale.service.TryBookingService;
import com.ebious.aviasale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/booking")
@Validated
public class BookingController {

    private final TryBookingService tryBookingService;
    private final UserService userService;
    private final BookingsService bookingsService;

    @Autowired
    public BookingController(TryBookingService tryBookingService,
                             UserService userService,
                             BookingsService bookingsService) {
        this.tryBookingService = tryBookingService;
        this.userService = userService;
        this.bookingsService = bookingsService;
    }

    @PostMapping("/try-booking")
    public void tryBooking(@RequestBody @NotNull PreBookingDto preBookingDto, Principal principal)
            throws InternalEbiousAppErrorEx, InvalidUserDataException, BookingFailedException {
        Users userByEmail = userService.getUserByEmail(principal.getName());
        tryBookingService.tryBooking(preBookingDto, userByEmail);
    }

    @DeleteMapping("/delete-booking/{bookRef}")
    public void deleteBooking(@PathVariable("bookRef") @NotBlank String bookRef) throws BookingNotFoundException {
        bookingsService.deleteBooking(bookingsService.getBookingByBookRef(bookRef));
    }

    @GetMapping("/bookings")
    public List<BookingDto> getBookingByUser(Principal principal) throws InternalEbiousAppErrorEx {
        Users users = userService.getUserByEmail(principal.getName());
        return tryBookingService.getBookingDto(users);
    }
}