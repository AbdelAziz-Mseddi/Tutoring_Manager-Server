package server.service;

import server.dto.PaymentRequest;
import server.dto.PaymentResponse;
import server.entity.Payment;
import server.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
// Business logic for payment operations and DTO mapping.
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Page<PaymentResponse> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public PaymentResponse getPaymentById(Integer id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
        return toResponse(payment);
    }

    public PaymentResponse createPayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setTotalAmount(request.getTotalAmount());
        payment.setNotes(request.getNotes());
        return toResponse(paymentRepository.save(payment));
    }

    public PaymentResponse updatePayment(Integer id, PaymentRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
        payment.setTotalAmount(request.getTotalAmount());
        payment.setNotes(request.getNotes());
        return toResponse(paymentRepository.save(payment));
    }

    public void deletePayment(Integer id) {
        if (!paymentRepository.existsById(id)) {
            throw new RuntimeException("Payment not found with ID: " + id);
        }
        paymentRepository.deleteById(id);
    }

    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getTotalAmount(),
                payment.getPaidAt(),
                payment.getNotes()
        );
    }
}
