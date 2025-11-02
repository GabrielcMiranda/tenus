package miranda.gabriel.tenus.adapters.inbounds.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import miranda.gabriel.tenus.adapters.inbounds.dto.address.AddressRequestDTO;
import miranda.gabriel.tenus.adapters.inbounds.dto.task.TaskResponseDTO;
import miranda.gabriel.tenus.application.usecases.AddressUsecases;
import miranda.gabriel.tenus.application.usecases.TaskUsecases;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final AddressUsecases addressService;
    private final TaskUsecases taskService;

    @PostMapping("/{id}/address")
    public ResponseEntity<String> registerAddress(@PathVariable Long id,@RequestBody AddressRequestDTO dto, @AuthenticationPrincipal Jwt jwt) {

        addressService.registerAddress(AddressUsecases.TaskType.TASK, id, dto, jwt.getSubject());
        return ResponseEntity.status(201).body("Address created successfully");
    }

    @PutMapping("/{id}/address")
    public ResponseEntity<String> updateAddress(@PathVariable Long id,@RequestBody AddressRequestDTO dto, @AuthenticationPrincipal Jwt jwt) {

        addressService.updateAddress(AddressUsecases.TaskType.TASK, id, dto, jwt.getSubject());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTask(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        var task = taskService.getTask(id, jwt.getSubject());
        return ResponseEntity.ok(task);
    }

    @GetMapping("/{id}/address")
    public ResponseEntity<AddressRequestDTO> getTaskAddress(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        var address = taskService.getTaskAddress(id, jwt.getSubject());
        return ResponseEntity.ok(address);
    }

}
