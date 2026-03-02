package com.lmejia.springboot.policies.app.policiesapp.mapper;

import com.lmejia.springboot.policies.app.policiesapp.dto.ClientDTO;
import com.lmejia.springboot.policies.app.policiesapp.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDTO toDto(Client client);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    Client toEntity(ClientDTO clientDTO);

    List<ClientDTO> toDtoList(List<Client> clients);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    void updateEntityFromDto(ClientDTO clientDTO, @MappingTarget Client client);
}
