package com.example.advprogticket;

import com.example.advprogticket.model.Ticket;
import com.example.advprogticket.repository.TicketRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerUnitTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketRepository ticketRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenGetTickets_thenReturnJsonTickets() throws Exception {
        Ticket ticket1 = new Ticket(1, "BET001", "Ruben", "Boone", "r0785519@student.thomasmore.be", new Date());
        Ticket ticket2 = new Ticket(2, "FRT001", "Ruben", "Boone", "r0785519@student.thomasmore.be", new Date());

        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket1);
        ticketList.add(ticket2);

        given(ticketRepository.findAll()).willReturn(ticketList);

        mockMvc.perform(get("/tickets"))
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
    public void givenEmail_whenGetTicketsByEmail_thenReturnJsonTicket() throws Exception {

        Ticket ticket1 = new Ticket(1, "BET001", "Ruben", "Boone", "r0785519@student.thomasmore.be", new Date());
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket1);

        given(ticketRepository.findTicketByEmail("r0785519@student.thomasmore.be")).willReturn(ticketList);

        mockMvc.perform(get("/tickets/{email}","r0785519@student.thomasmore.be"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tourCode", is("BET001")))
                .andExpect(jsonPath("$[0].firstName", is("Ruben")))
                .andExpect(jsonPath("$[0].lastName", is("Boone")))
                .andExpect(jsonPath("$[0].email", is("r0785519@student.thomasmore.be")));
    }

    @Test
    public void whenPostMonument_thenReturnJsonMonument() throws Exception{
        Ticket ticket1 = new Ticket(1, "BET001", "Ruben", "Boone", "r0785519@student.thomasmore.be", new Date());

        mockMvc.perform(post("/tickets")
                        .content(mapper.writeValueAsString(ticket1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tourCode", is("BET001")))
                .andExpect(jsonPath("$.firstName", is("Ruben")))
                .andExpect(jsonPath("$.lastName", is("Boone")))
                .andExpect(jsonPath("$.email", is("r0785519@student.thomasmore.be")));
    }

}
