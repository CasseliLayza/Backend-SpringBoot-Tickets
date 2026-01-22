package com.backend.springboot.tickets.service.imp;

import com.backend.springboot.tickets.entity.Developer;
import com.backend.springboot.tickets.entity.Ticket;
import com.backend.springboot.tickets.repository.DeveloperRepository;
import com.backend.springboot.tickets.repository.TicketRepository;
import com.backend.springboot.tickets.service.ITicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService implements ITicketService {
    private final Logger logger = LoggerFactory.getLogger(TicketService.class);

    private final TicketRepository ticketRepository;
    private final DeveloperRepository developerRepository;

    public TicketService(TicketRepository ticketRepository, DeveloperRepository developerRepository) {
        this.ticketRepository = ticketRepository;
        this.developerRepository = developerRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Ticket> listTickets() {

        List<Ticket> tickets = ticketRepository.findAll();
        logger.info("Retrieved {} tickets from the database.", tickets.size());

        return tickets;
    }


    @Override
    @Transactional
    public Ticket createTicket(Ticket ticket) {

        ticketRepository.findByTicketNumber(ticket.getTicketNumber())
                .ifPresent(t -> {
                    logger.warn("Ticket with number {} already exists. Creation aborted.", ticket.getTicketNumber());
                    throw new RuntimeException("Ticket with number " + ticket.getTicketNumber() + " already exists.");
                });

        Ticket newTicket = ticketRepository.save(ticket);
        logger.info("Created new ticket with ID {}: {}", newTicket.getId(), newTicket);
        return newTicket;
    }

    @Override
    @Transactional(readOnly = true)
    public Ticket getTicketById(Long id) {

        Ticket ticket = ticketRepository.findById(id).orElse(null);

        logger.info("Retrieved ticket with ID {}: {}", id, ticket);

        return ticket;
    }

    @Override
    @Transactional
    public Ticket updateTicket(Ticket ticket, Long id) {

        return ticketRepository.findById(id)
                .map(t -> {
                    t.setId(id);
                    t.setTitle(ticket.getTitle());
                    t.setDescription(ticket.getDescription());
                    t.setStatus(ticket.getStatus());
                    t.setPriority(ticket.getPriority());
                    t.setCreatedAt(ticket.getCreatedAt());
                    t.setUpdatedAt(ticket.getUpdatedAt());
                    t.setDevelopers(ticket.getDevelopers());
                    Ticket updatedTicket = ticketRepository.save(t);
                    logger.info("Ticket with ID {} updated successfully.", id);
                    return updatedTicket;
                })
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));
    }

    @Override
    @Transactional
    public void deleteTicket(Long id) {

        ticketRepository.deleteById(id);
        logger.info("Ticket with ID {} deleted successfully.", id);

    }

    @Override
    @Transactional
    public Ticket assignDeveloperToTicket(Long ticketId, Long developerId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + ticketId));

        Developer developerBuscado = developerRepository.findById(developerId)
                .orElseThrow(() -> new RuntimeException("Developer not found with id " + developerId));

        ticket.getDevelopers().add(developerBuscado);
        developerBuscado.getTickets().add(ticket);

        Ticket updatedTicket = ticketRepository.save(ticket);

        logger.info("Assigned developer with ID {} to ticket with ID {}.", developerId, ticketId);

        return updatedTicket;
    }
}
