package com.ebious.aviasale.repository;

import com.ebious.aviasale.domain.entity.BoardingPasses;
import com.ebious.aviasale.domain.entity.compositeKeys.TicketNoFlightIdPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardingPassesRepository extends JpaRepository<BoardingPasses, TicketNoFlightIdPK> {

}
