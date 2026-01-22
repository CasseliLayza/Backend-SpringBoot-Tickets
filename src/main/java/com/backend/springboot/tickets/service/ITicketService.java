package com.backend.springboot.tickets.service;

import com.backend.springboot.tickets.entity.Ticket;

import java.util.List;

public interface ITicketService {

    List<Ticket>listTickets();
    Ticket createTicket(Ticket ticket);
    Ticket getTicketById(Long id);
    Ticket updateTicket(Ticket ticket, Long id);
    void deleteTicket(Long id);
    Ticket assignDeveloperToTicket(Long ticketId, Long developerId);
}
