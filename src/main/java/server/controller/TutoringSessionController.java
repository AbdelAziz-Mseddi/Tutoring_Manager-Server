package server.controller;

import server.dto.TutoringSessionRequest;
import server.dto.TutoringSessionResponse;
import server.service.TutoringSessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutoring-sessions")
// HTTP endpoints for tutoring session CRUD operations and pagination.
public class TutoringSessionController {

    @Autowired
    private TutoringSessionService tutoringSessionService;

    @GetMapping
    public ResponseEntity<Page<TutoringSessionResponse>> getAllSessions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(tutoringSessionService.getAllSessions(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutoringSessionResponse> getSessionById(@PathVariable Integer id) {
        return ResponseEntity.ok(tutoringSessionService.getSessionById(id));
    }

    @PostMapping
    public ResponseEntity<TutoringSessionResponse> createSession(@Valid @RequestBody TutoringSessionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tutoringSessionService.createSession(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TutoringSessionResponse> updateSession(@PathVariable Integer id, @Valid @RequestBody TutoringSessionRequest request) {
        return ResponseEntity.ok(tutoringSessionService.updateSession(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(@PathVariable Integer id) {
        tutoringSessionService.deleteSession(id);
        return ResponseEntity.noContent().build();
    }
}
