package server.controller;

import server.dto.TutoringClassRequest;
import server.dto.TutoringClassResponse;
import server.service.TutoringClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutoring-classes")
// HTTP endpoints for tutoring class CRUD operations and pagination.
public class TutoringClassController {

    @Autowired
    private TutoringClassService tutoringClassService;

    @GetMapping
    public ResponseEntity<Page<TutoringClassResponse>> getAllClasses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(tutoringClassService.getAllClasses(getAuthenticatedUserId(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutoringClassResponse> getClassById(@PathVariable Integer id) {
        return ResponseEntity.ok(tutoringClassService.getClassById(id, getAuthenticatedUserId()));
    }

    @PostMapping
    public ResponseEntity<TutoringClassResponse> createClass(@Valid @RequestBody TutoringClassRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tutoringClassService.createClass(request, getAuthenticatedUserId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutoringClassResponse> updateClass(@PathVariable Integer id, @Valid @RequestBody TutoringClassRequest request) {
        return ResponseEntity.ok(tutoringClassService.updateClass(id, request, getAuthenticatedUserId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable Integer id) {
        tutoringClassService.deleteClass(id, getAuthenticatedUserId());
        return ResponseEntity.noContent().build();
    }

    private Integer getAuthenticatedUserId() {
        return (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
