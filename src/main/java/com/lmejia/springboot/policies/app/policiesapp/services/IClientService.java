package com.lmejia.springboot.policies.app.policiesapp.services;

import com.lmejia.springboot.policies.app.policiesapp.dto.ClientDTO;
import com.lmejia.springboot.policies.app.policiesapp.dto.PageResponse;
import com.lmejia.springboot.policies.app.policiesapp.entities.Client;

import java.util.List;
import java.util.Optional;

public interface IClientService {
    Optional<Client> getClientById(Long id);

    PageResponse<ClientDTO> findAllClients(int page, int size, String sortBy, String sortDirection);

    Client saveClient(Client client);

    Client updateClient(Long id, ClientDTO client);

    void deleteClient(Long id);
}
