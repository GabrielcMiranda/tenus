package miranda.gabriel.tenus.adapters.inbounds.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.address.AddressRequestDTO;
import miranda.gabriel.tenus.application.usecases.AddressUsecases;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final AddressUsecases addressService;

    @PostMapping("/{id}/address")
    public ResponseEntity<String> registerAddress(@PathVariable Long id,@RequestBody AddressRequestDTO dto, @AuthenticationPrincipal Jwt jwt) {

        addressService.registerAddress(AddressUsecases.TaskType.TASK, id, dto, jwt.getSubject());
        return ResponseEntity.status(201).body("Address created successfully");
    }
    
}
