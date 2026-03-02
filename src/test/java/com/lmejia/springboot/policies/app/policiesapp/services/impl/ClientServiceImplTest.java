package com.lmejia.springboot.policies.app.policiesapp.services.impl;

import com.lmejia.springboot.policies.app.policiesapp.dto.ClientDTO;
import com.lmejia.springboot.policies.app.policiesapp.entities.Client;
import com.lmejia.springboot.policies.app.policiesapp.entities.IdentificationTypeEnum;
import com.lmejia.springboot.policies.app.policiesapp.exceptions.BusinessRuleException;
import com.lmejia.springboot.policies.app.policiesapp.exceptions.ResourceNotFoundException;
import com.lmejia.springboot.policies.app.policiesapp.mapper.ClientMapper;
import com.lmejia.springboot.policies.app.policiesapp.repository.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    void saveClient_Success() {
        Client client = new Client();
        client.setIdentificationType(IdentificationTypeEnum.CC);
        client.setBirthDate(LocalDateTime.now().minusYears(20));

        when(clientRepository.save(any(Client.class))).thenReturn(client);

        Client result = clientService.saveClient(client);

        assertNotNull(result);
        verify(clientRepository).save(client);
    }

    @Test
    void saveClient_ThrowsException_WhenTI() {
        Client client = new Client();
        client.setIdentificationType(IdentificationTypeEnum.TI);

        assertThrows(BusinessRuleException.class, () -> clientService.saveClient(client));
    }

    @Test
    void saveClient_ThrowsException_WhenUnderage() {
        Client client = new Client();
        client.setIdentificationType(IdentificationTypeEnum.CC);
        client.setBirthDate(LocalDateTime.now().minusYears(17));
        assertThrows(BusinessRuleException.class, () -> clientService.saveClient(client));
    }

    @Test
    void updateClient_NotFound() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> clientService.updateClient(1L, new ClientDTO()));
    }
}
