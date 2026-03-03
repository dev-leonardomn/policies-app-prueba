package com.lmejia.springboot.policies.app.policiesapp.controllers;

import com.lmejia.springboot.policies.app.policiesapp.dto.ClientDTO;
import com.lmejia.springboot.policies.app.policiesapp.dto.PageResponse;
import com.lmejia.springboot.policies.app.policiesapp.entities.Client;
import com.lmejia.springboot.policies.app.policiesapp.exceptions.ResourceNotFoundException;
import com.lmejia.springboot.policies.app.policiesapp.mapper.ClientMapper;
import com.lmejia.springboot.policies.app.policiesapp.services.IClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Operation related for managing clients")
public class ClientController {

    private final IClientService clientService;
    private final ClientMapper clientMapper;

    @Operation(summary = "Get client by id", description = "Returns a client if it exists")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client exists"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Client doesn't exist")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findById(@Parameter(description = "Client identifier", example = "1", required = true) @PathVariable Long id) {
        return clientService.getClientById(id).map(clientMapper::toDto).map(ResponseEntity::ok).orElseThrow(() -> new ResourceNotFoundException("Client not found."));
    }

    @Operation(summary = "Get client list", description = "Returns the complete list of registered clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clients retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<PageResponse<ClientDTO>> findAll(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "ASC") String sortDirection) {
        return ResponseEntity.ok(clientService.findAllClients(page, size, sortBy, sortDirection));
    }

    @Operation(
            summary = "Create client",
            description = "Creates a new client in the system"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<ClientDTO> save(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Client information",
            required = true
    ) @RequestBody ClientDTO clientDTO) {
        Client client = clientMapper.toEntity(clientDTO);
        Client savedClient = clientService.saveClient(client);

        return ResponseEntity.status(HttpStatus.CREATED).body(clientMapper.toDto(savedClient));
    }

    @Operation(
            summary = "Update client",
            description = "Updates an existing client by id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> update(@Parameter(description = "Client identifier", example = "1", required = true) @PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        Client updatedClient = clientService.updateClient(id, clientDTO);
        return ResponseEntity.ok(clientMapper.toDto(updatedClient));
    }

    @Operation(
            summary = "Delete client",
            description = "Deletes a client by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client deleted succesfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "404", description = "Client doesn't exist")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Parameter(description = "Client identifier", example = "1", required = true) @PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
