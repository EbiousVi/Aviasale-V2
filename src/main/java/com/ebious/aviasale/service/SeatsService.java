package com.ebious.aviasale.service;

import com.ebious.aviasale.domain.projection.IFreeSeats;
import com.ebious.aviasale.domain.apiDto.resp.SeatDto;
import com.ebious.aviasale.domain.projection.FreeSeats;
import com.ebious.aviasale.domain.enums.CabinLayout;
import com.ebious.aviasale.repository.SeatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static java.util.stream.Collectors.toList;

@Service
public class SeatsService {

    private final SeatsRepository seatsRepository;

    @Autowired
    public SeatsService(SeatsRepository seatsRepository) {
        this.seatsRepository = seatsRepository;
    }

    /**
     * Find all seats by fare condition and check if they are free.
     * Then create Map where key is row number and value is List of seats corresponding row number.
     * Then get Cabin layout by aircraft code and fare condition.
     * And insert aisles to every row.
     */
    public List<List<SeatDto>> buildCabinLayout(Integer flightId, String aircraftCode, String fareConditions) {
        List<IFreeSeats> seats = seatsRepository.findAllSeatsAndCheckIsFree(
                flightId, aircraftCode, fareConditions
        );
        Map<Integer, List<SeatDto>> rows = this.collectRows(seats);
        List<String> cabinLayout = CabinLayout.valueOf("ABC_" + aircraftCode + "_" + fareConditions.toUpperCase()).layout;
        return this.insertAisle(rows, cabinLayout);
    }

    /**
     * Convert seats projection {@link FreeSeats}, {@link IFreeSeats} to SeatDto {@link SeatDto}
     * Then grouping by Seat Number like Integer and sort List of SeatsDto in alphabetical order.
     *
     * @param seats - List of projection interface. Interface get seat number and boolean value is seats are free.
     *              for more info {@link SeatsRepository#findAllSeatsAndCheckIsFree(Integer, String, String)}
     * @return Map where key is seat number and List of SeatDto corresponding row number.
     * Map sorted by natural order because keys is Integer.
     */
    Map<Integer, List<SeatDto>> collectRows(List<IFreeSeats> seats) {
        return seats.stream()
                .map(sd -> new SeatDto(sd.getSeatNo(), sd.getFree()))
                .collect(groupingBy(sd -> Integer.parseInt(sd.getSeatNo().replaceAll("\\D", "")),
                        mapping(Function.identity(),
                                collectingAndThen(toList(),
                                        list -> list.stream()
                                                .sorted((o1, o2) -> {
                                                    String s1 = o1.getSeatNo().substring(o1.getSeatNo().length() - 1);
                                                    String s2 = o2.getSeatNo().substring(o1.getSeatNo().length() - 1);
                                                    return s1.compareTo(s2);
                                                })
                                                .collect(toList())))));
    }

    /**
     * The method inserts empty spaces and aisles according to the cabin layout.
     * Some rows may contain less seat than the template, but a row cannot contain more space than the template.
     *
     * @param rows        {@link #collectRows(List) method}
     * @param cabinLayout {@link CabinLayout}
     * @return List of List<SeatDto> fully matching of Cabin layout
     */
    List<List<SeatDto>> insertAisle(Map<Integer, List<SeatDto>> rows, List<String> cabinLayout) {
        return rows.values().stream()
                .flatMap(row -> {
                    for (int rowIndex = 0; rowIndex < row.size(); rowIndex++) {
                        if (cabinLayout.get(rowIndex).equals("AISLE")) {
                            row.add(rowIndex, new SeatDto("AISLE", Boolean.FALSE));
                        }
                        if (!row.get(rowIndex).getSeatNo().contains(cabinLayout.get(rowIndex))) {
                            row.add(rowIndex, new SeatDto("", Boolean.FALSE));
                        }
                        if (rowIndex == row.size() - 1) {
                            int remainder = cabinLayout.size() - row.size();
                            for (int i = 0; i < remainder; i++) {
                                if (cabinLayout.get((rowIndex + 1) + i).equals("AISLE")) {
                                    row.add(new SeatDto("AISLE", Boolean.FALSE));
                                } else {
                                    row.add(new SeatDto("", Boolean.FALSE));
                                }
                            }
                            break;
                        }
                    }
                    return Stream.of(row);
                })
                .collect(toList());
    }
}
