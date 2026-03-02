package com.lmejia.springboot.policies.app.policiesapp.services.impl;

import com.lmejia.springboot.policies.app.policiesapp.dto.ClientDTO;
import com.lmejia.springboot.policies.app.policiesapp.entities.Client;
import com.lmejia.springboot.policies.app.policiesapp.entities.IdentificationTypeEnum;
import com.lmejia.springboot.policies.app.policiesapp.exceptions.BusinessRuleException;
import com.lmejia.springboot.policies.app.policiesapp.exceptions.ResourceNotFoundException;
import com.lmejia.springboot.policies.app.policiesapp.mapper.ClientMapper;
import com.lmejia.springboot.policies.app.policiesapp.repository.ClientRepository;
import com.lmejia.springboot.policies.app.policiesapp.services.IClientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements IClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    @Transactional
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional
    public Client saveClient(Client client) {
        validateClientLogic(client);
        client.setId(null);
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client updateClient(Long id, ClientDTO client) {
        return clientRepository.findById(id).map(existingClient -> {
                    clientMapper.updateEntityFromDto(client, existingClient);
                    return clientRepository.save(existingClient);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {
        if (!clientRepository.existsById(id)) throw new ResourceNotFoundException("Client not found");
        clientRepository.deleteById(id);
    }

    private void validateClientLogic(Client client) {
        if (client.getIdentificationType() == IdentificationTypeEnum.TI) {
            throw new BusinessRuleException("TI document type is not allowed for registration.");
        }

        if (client.getBirthDate() != null) {
            int age = Period.between(client.getBirthDate().toLocalDate(), LocalDate.now()).getYears();
            if (age < 18) {
                throw new BusinessRuleException("Client must be at least 18 years old.");
            }
        }
    }
}
