package com.example.advprogticket.controller;

import com.example.advprogticket.model.Ticket;
import com.example.advprogticket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;

@RestController
public class TicketController {

    @Autowired
    private TicketRepository ticketRepository;

    @PostConstruct
    void fillDb(){
        if (ticketRepository.count() == 0){
            ticketRepository.save(new Ticket(1, "BET001", "Ruben", "Boone", "r0785519@student.thomasmore.be", new Date()));
            ticketRepository.save(new Ticket(2, "FRT001", "Ruben", "Boone", "r0785519@student.thomasmore.be", new Date()));
            ticketRepository.save(new Ticket(3, "FRT001", "Ruben", "Boone", "r0785519@student.thomasmore.be", new Date()));
            ticketRepository.save(new Ticket(4, "UST001", "Ruben", "Boone", "r0785519@student.thomasmore.be", new Date()));
            ticketRepository.save(new Ticket(5, "BET001", "Ruben", "Boone", "r0785519@student.thomasmore.be", new Date()));
            ticketRepository.save(new Ticket(6, "BET001", "Ruben", "Boone", "r0785519@student.thomasmore.be", new Date()));
            ticketRepository.save(new Ticket(7, "BET002", "Ruben", "Boone", "r0785519@student.thomasmore.be", new Date()));
            ticketRepository.save(new Ticket(8, "BET002", "Ruben", "Boone", "r0785519@student.thomasmore.com", new Date()));
        }

        System.out.println("Info test: " + ticketRepository);

    }

    @GetMapping("/tickets")
    public List<Ticket> getAllTickets(){
        return ticketRepository.findAll();
    }

    @GetMapping("/tickets/{email}")
    public List<Ticket> getAllTicketsByEmail(@PathVariable String email){
        return ticketRepository.findTicketByEmail(email);
    }

    @PostMapping("/tickets")
    public Ticket addTicket(@RequestBody Ticket ticket){
        ticketRepository.save(ticket);
        return ticket;
    }
}
