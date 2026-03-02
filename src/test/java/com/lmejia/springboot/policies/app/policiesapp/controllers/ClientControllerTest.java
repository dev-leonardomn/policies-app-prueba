package com.lmejia.springboot.policies.app.policiesapp.controllers;

import com.lmejia.springboot.policies.app.policiesapp.dto.ClientDTO;
import com.lmejia.springboot.policies.app.policiesapp.entities.Client;
import com.lmejia.springboot.policies.app.policiesapp.mapper.ClientMapper;
import com.lmejia.springboot.policies.app.policiesapp.services.IClientService;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IClientService clientService;

    @MockitoBean
    private ClientMapper clientMapper;

    @Test
    void findById_ShouldReturnClient() throws Exception {
        Client client = new Client();
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName("John");

        when(clientService.getClientById(1L)).thenReturn(Optional.of(client));
        when(clientMapper.toDto(client)).thenReturn(clientDTO);

        mockMvc.perform(get("/api/client/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void findById_ShouldReturn404() throws Exception {
        when(clientService.getClientById(1L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/client/1")).andExpect(status().isNotFound());
    }
}
