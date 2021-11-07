package com.ebious.aviasale.repository;

import com.ebious.aviasale.domain.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookingsRepository extends JpaRepository<Bookings, String> {
    Optional<Bookings> findByBookRef(String bookRef);

    @Query(value = "SELECT * FROM bookings WHERE user_id =:userId", nativeQuery = true)
    List<Bookings> findAllByUser(Long userId);
}
