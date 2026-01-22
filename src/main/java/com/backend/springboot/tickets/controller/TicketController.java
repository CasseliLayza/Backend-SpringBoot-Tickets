package com.backend.springboot.tickets.controller;


import com.backend.springboot.tickets.entity.Ticket;
import com.backend.springboot.tickets.service.ITicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    private final ITicketService ticketService;

    public TicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
    }


    @GetMapping
    public List<Ticket> listTickets() {
        return ticketService.listTickets();
    }

    @PostMapping
    public Ticket createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);

    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@RequestBody Ticket ticket, @PathVariable Long id) {
        return ticketService.updateTicket(ticket, id);
    }

    @DeleteMapping("/{id}")
    public void deleteTicket(@PathVariable  Long id) {
        ticketService.deleteTicket(id);
    }


    @PostMapping("/assign/{ticketId}/developer/{developerId}")
    public Ticket assignDeveloperToTicket(@PathVariable Long ticketId, @PathVariable Long developerId) {
        return ticketService.assignDeveloperToTicket(ticketId, developerId);

    }
}
