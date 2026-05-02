package server.service;

import server.dto.PaymentRequest;
import server.dto.PaymentResponse;
import server.entity.Payment;
import server.entity.User;
import server.exception.ForbiddenException;
import server.repository.PaymentRepository;
import server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
// Business logic for payment operations and DTO mapping.
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<PaymentResponse> getAllPayments(Integer userId, Pageable pageable) {
        return paymentRepository.findAllByUser_Id(userId, pageable)
                .map(this::toResponse);
    }

    public PaymentResponse getPaymentById(Integer id, Integer userId) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
        verifyOwnership(payment.getUser().getId(), userId);
        return toResponse(payment);
    }

    public PaymentResponse createPayment(PaymentRequest request, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setTotalAmount(request.getTotalAmount());
        payment.setNotes(request.getNotes());
        return toResponse(paymentRepository.save(payment));
    }

    public PaymentResponse updatePayment(Integer id, PaymentRequest request, Integer userId) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
        verifyOwnership(payment.getUser().getId(), userId);
        payment.setTotalAmount(request.getTotalAmount());
        payment.setNotes(request.getNotes());
        return toResponse(paymentRepository.save(payment));
    }

    public void deletePayment(Integer id, Integer userId) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
        verifyOwnership(payment.getUser().getId(), userId);
        paymentRepository.deleteById(id);
    }

    private void verifyOwnership(Integer resourceUserId, Integer authenticatedUserId) {
        if (!resourceUserId.equals(authenticatedUserId)) {
            throw new ForbiddenException("Access denied");
        }
    }

    private PaymentResponse toResponse(Payment payment) {
        return new PaymentResponse(
                payment.getId(),
                payment.getUser().getId(),
                payment.getTotalAmount(),
                payment.getPaidAt(),
                payment.getNotes()
        );
    }
}
