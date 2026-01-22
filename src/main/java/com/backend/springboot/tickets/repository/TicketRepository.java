package com.backend.springboot.tickets.repository;

import com.backend.springboot.tickets.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {


    Optional<Ticket> findByTicketNumber(String ticketNumber);
}
