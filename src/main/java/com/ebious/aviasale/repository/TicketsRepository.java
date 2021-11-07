package com.ebious.aviasale.repository;

import com.ebious.aviasale.domain.entity.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketsRepository extends JpaRepository<Tickets, String> {

}
