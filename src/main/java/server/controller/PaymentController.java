package server.controller;

import server.dto.PaymentRequest;
import server.dto.PaymentResponse;
import server.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
// HTTP endpoints for payment CRUD operations and pagination.
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<Page<PaymentResponse>> getAllPayments(
            @RequestParam Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(paymentService.getAllPayments(userId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponse> getPaymentById(@PathVariable Integer id) {
        return ResponseEntity.ok(paymentService.getPaymentById(id));
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.createPayment(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable Integer id, @Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.updatePayment(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
