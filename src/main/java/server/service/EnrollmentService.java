package server.service;

import server.dto.EnrollmentRequest;
import server.dto.EnrollmentResponse;
import server.entity.Enrollment;
import server.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
// Business logic for enrollments, including defaults, validation, and DTO mapping.
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public Page<EnrollmentResponse> getAllEnrollments(Pageable pageable) {
        return enrollmentRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public EnrollmentResponse getEnrollmentById(Integer id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + id));
        return toResponse(enrollment);
    }

    public EnrollmentResponse createEnrollment(EnrollmentRequest request) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(request.getStudentId());
        enrollment.setClassId(request.getClassId());
        enrollment.setEnrolledAt(request.getEnrolledAt() != null ? request.getEnrolledAt() : LocalDate.now());
        enrollment.setStatus(request.getStatus() != null ? request.getStatus() : "active");
        return toResponse(enrollmentRepository.save(enrollment));
    }

    public EnrollmentResponse updateEnrollment(Integer id, EnrollmentRequest request) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + id));
        enrollment.setStudentId(request.getStudentId());
        enrollment.setClassId(request.getClassId());
        if (request.getEnrolledAt() != null) {
            enrollment.setEnrolledAt(request.getEnrolledAt());
        }
        if (request.getStatus() != null) {
            enrollment.setStatus(request.getStatus());
        }
        return toResponse(enrollmentRepository.save(enrollment));
    }

    public EnrollmentResponse updateEnrollmentStatus(Integer id, String status) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + id));
        enrollment.setStatus(status);
        return toResponse(enrollmentRepository.save(enrollment));
    }

    public void deleteEnrollment(Integer id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new RuntimeException("Enrollment not found with ID: " + id);
        }
        enrollmentRepository.deleteById(id);
    }

    private EnrollmentResponse toResponse(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getStudentId(),
                enrollment.getClassId(),
                enrollment.getEnrolledAt(),
                enrollment.getStatus()
        );
    }
}
