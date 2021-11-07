package com.ebious.aviasale.service;

import com.ebious.aviasale.domain.apiDto.req.PassengerDto;
import com.ebious.aviasale.domain.entity.Bookings;
import com.ebious.aviasale.domain.entity.Tickets;
import com.ebious.aviasale.domain.json.ContactsJson;
import com.ebious.aviasale.expection.InvalidUserDataException;
import com.ebious.aviasale.repository.TicketsRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TicketsService {
    private final TicketsRepository ticketsRepository;
    private static final Logger logger = LoggerFactory.getLogger(TicketsService.class);

    @Autowired
    public TicketsService(TicketsRepository ticketsRepository) {
        this.ticketsRepository = ticketsRepository;
    }

    /**
     * abbreviation PAX = passenger
     */
    public List<Tickets> createTickets(Bookings booking, List<PassengerDto> passengers) throws InvalidUserDataException {
        List<Tickets> tickets = new ArrayList<>();
        for (PassengerDto pax : passengers) {
            Tickets ticket = new Tickets();
            ticket.setTicketNo("T" + UUID.randomUUID().toString().substring(0, 12).toUpperCase());
            ticket.setBookRef(booking.getBookRef());
            ticket.setPassengerId(pax.getPassengerId());
            ticket.setFullName(pax.getFullName());
            ObjectMapper objectMapper = new ObjectMapper();
            ContactsJson contactsJson = new ContactsJson();
            contactsJson.setEmail(pax.getEmail());
            contactsJson.setPhone(pax.getPhoneNo());
            try {
                String contacts = objectMapper.writeValueAsString(contactsJson);
                ticket.setContactDataJsonb(contacts);
            } catch (JsonProcessingException e) {
                logger.error("ObjectMapper can't write value as string!", e);
            }
            tickets.add(ticket);
        }
        ticketsRepository.saveAll(tickets);
        return tickets;
    }
}
