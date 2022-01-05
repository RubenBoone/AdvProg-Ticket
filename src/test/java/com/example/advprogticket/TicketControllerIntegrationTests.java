package com.example.advprogticket;

import com.example.advprogticket.model.Ticket;
import com.example.advprogticket.repository.TicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    private Ticket ticket1 = new Ticket(1, "BET001", "Ruben", "Boone", "r0785519@student.thomasmore.be", new Date());
    private Ticket ticket2 = new Ticket(2, "FRT001", "Ruben", "Boone", "r0785519@student.thomasmore.be", new Date());
    private Ticket ticket3 = new Ticket(3, "FRT001", "Joppe", "Peeters", "r0802173@student.thomasmore.be", new Date());
    private Ticket ticketToDelete = new Ticket(4, "deleteMe", "deleteMe", "deleteMe", "deleteMe", new Date());

    @BeforeEach
    public void beforeAllTests() {
        ticketRepository.deleteAll();
        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
        ticketRepository.save(ticket3);
        ticketRepository.save(ticketToDelete);
    }

    @AfterEach
    public void afterAllTests() {
        ticketRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenGetTickets_thenReturnJsonTickets() throws Exception {

        mockMvc.perform(get("/tickets"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].tourCode", is("BET001")))
                .andExpect(jsonPath("$[0].firstName", is("Ruben")))
                .andExpect(jsonPath("$[0].lastName", is("Boone")))
                .andExpect(jsonPath("$[0].email", is("r0785519@student.thomasmore.be")))

                .andExpect(jsonPath("$[1].tourCode", is("FRT001")))
                .andExpect(jsonPath("$[1].firstName", is("Ruben")))
                .andExpect(jsonPath("$[1].lastName", is("Boone")))
                .andExpect(jsonPath("$[1].email", is("r0785519@student.thomasmore.be")))

                .andExpect(jsonPath("$[2].tourCode", is("FRT001")))
                .andExpect(jsonPath("$[2].firstName", is("Joppe")))
                .andExpect(jsonPath("$[2].lastName", is("Peeters")))
                .andExpect(jsonPath("$[2].email", is("r0802173@student.thomasmore.be")));
    }

    @Test
    public void givenEmail_whenGetTicketsByEmail_thenReturnJsonTickets() throws Exception {

        mockMvc.perform(get("/tickets/{email}", "r0785519@student.thomasmore.be"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].tourCode", is("BET001")))
                .andExpect(jsonPath("$[0].firstName", is("Ruben")))
                .andExpect(jsonPath("$[0].lastName", is("Boone")))
                .andExpect(jsonPath("$[0].email", is("r0785519@student.thomasmore.be")))

                .andExpect(jsonPath("$[1].tourCode", is("FRT001")))
                .andExpect(jsonPath("$[1].firstName", is("Ruben")))
                .andExpect(jsonPath("$[1].lastName", is("Boone")))
                .andExpect(jsonPath("$[1].email", is("r0785519@student.thomasmore.be")));
    }

    @Test
    public void whenPostTicket_thenReturnJsonTicket() throws Exception {

        Ticket newTicket = new Ticket(5, "BET001", "Joppe", "Peeters", "r0802173@student.thomasmore.be", new Date());

        mockMvc.perform(post("/tickets")
                        .content(mapper.writeValueAsString(newTicket))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tourCode", is("BET001")))
                .andExpect(jsonPath("$.firstName", is("Joppe")))
                .andExpect(jsonPath("$.lastName", is("Peeters")))
                .andExpect(jsonPath("$.email", is("r0802173@student.thomasmore.be")));
    }
}
