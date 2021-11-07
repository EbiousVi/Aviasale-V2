package com.ebious.aviasale.service;

import com.ebious.aviasale.domain.apiDto.resp.AirportDto;
import com.ebious.aviasale.repository.AirportsDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportsDataService {
    final private AirportsDataRepository airportsDataRepository;

    @Autowired
    public AirportsDataService(AirportsDataRepository airportsDataRepository) {
        this.airportsDataRepository = airportsDataRepository;
    }

    public List<AirportDto> getAllAirportsDto() {
        return airportsDataRepository.findAllRuOnly().stream()
                .map(a -> {
                    if (a.getAirportName().equals(a.getCity())) {
                        return new AirportDto(a.getAirportCode(), "", a.getAirportName());
                    } else {
                        return new AirportDto(a.getAirportCode(), a.getCity(), a.getAirportName());
                    }
                })
                .collect(Collectors.toList());
    }
}
