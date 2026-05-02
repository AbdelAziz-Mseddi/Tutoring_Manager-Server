package server.controller;

import server.dto.EnrollmentRequest;
import server.dto.EnrollmentResponse;
import server.service.EnrollmentService;
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
@RequestMapping("/enrollments")
// HTTP endpoints for enrollment CRUD operations, status updates, and pagination.
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<Page<EnrollmentResponse>> getAllEnrollments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(enrollmentService.getAllEnrollments(getAuthenticatedUserId(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> getEnrollmentById(@PathVariable Integer id) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentById(id, getAuthenticatedUserId()));
    }

    @PostMapping
    public ResponseEntity<EnrollmentResponse> createEnrollment(@Valid @RequestBody EnrollmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentService.createEnrollment(request, getAuthenticatedUserId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> updateEnrollment(@PathVariable Integer id, @Valid @RequestBody EnrollmentRequest request) {
        return ResponseEntity.ok(enrollmentService.updateEnrollment(id, request, getAuthenticatedUserId()));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<EnrollmentResponse> updateEnrollmentStatus(@PathVariable Integer id, @RequestParam String status) {
        return ResponseEntity.ok(enrollmentService.updateEnrollmentStatus(id, status, getAuthenticatedUserId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEnrollment(@PathVariable Integer id) {
        enrollmentService.deleteEnrollment(id, getAuthenticatedUserId());
        return ResponseEntity.noContent().build();
    }

    private Integer getAuthenticatedUserId() {
        return (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
