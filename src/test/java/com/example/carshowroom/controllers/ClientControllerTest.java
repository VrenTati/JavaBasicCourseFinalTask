package com.example.carshowroom.controllers;

import com.example.carshowroom.controllers.ClientController;
import com.example.carshowroom.data.Client;
import com.example.carshowroom.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    }

    @Test
    void getClients_shouldReturnListOfClients() throws Exception {
        when(clientService.findClientsWithoutSuppliers()).thenReturn(Arrays.asList(new Client(), new Client()));

        mockMvc.perform(get("/clients"))
                .andExpect(status().isOk())
                .andExpect(view().name("client/clients"))
                .andExpect(model().attributeExists("clients"));

        verify(clientService, times(1)).findClientsWithoutSuppliers();
        verifyNoMoreInteractions(clientService);
    }

    @Test
    void modifyCharacteristics_shouldRedirectToClientInfo() throws Exception {
        int clientId = 1;
        Client client = new Client();
        client.setId(clientId);

        when(clientService.getClient(clientId)).thenReturn(Optional.of(client));

        mockMvc.perform(post("/client_info")
                        .param("id", String.valueOf(clientId)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/client_info/" + clientId));

        verify(clientService, times(1)).getClient(clientId);
        verify(clientService, times(1)).updateClient(client);
        verifyNoMoreInteractions(clientService);
    }


}
