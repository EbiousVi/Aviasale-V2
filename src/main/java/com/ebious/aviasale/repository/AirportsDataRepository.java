package com.ebious.aviasale.repository;

import com.ebious.aviasale.domain.entity.AirportsData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirportsDataRepository extends JpaRepository<AirportsData, String> {

    @Query(value = "SELECT " +
            "airport_code, " +
            "airport_name ->> 'ru' AS airport_name, " +
            "city ->> 'ru' AS city, coordinates, " +
            "timezone FROM airports_data " +
            "ORDER BY airport_name", nativeQuery = true)
    List<AirportsData> findAllRuOnly();
}
