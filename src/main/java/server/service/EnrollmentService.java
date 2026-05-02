package server.service;

import server.dto.EnrollmentRequest;
import server.dto.EnrollmentResponse;
import server.entity.Enrollment;
import server.entity.Student;
import server.exception.ForbiddenException;
import server.repository.EnrollmentRepository;
import server.repository.StudentRepository;
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

    @Autowired
    private StudentRepository studentRepository;

    public Page<EnrollmentResponse> getAllEnrollments(Integer userId, Pageable pageable) {
        return enrollmentRepository.findAllByStudent_User_Id(userId, pageable)
                .map(this::toResponse);
    }

    public EnrollmentResponse getEnrollmentById(Integer id, Integer userId) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + id));
        verifyOwnership(enrollment.getStudentId(), userId);
        return toResponse(enrollment);
    }

    public EnrollmentResponse createEnrollment(EnrollmentRequest request, Integer userId) {
        verifyOwnership(request.getStudentId(), userId);
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(request.getStudentId());
        enrollment.setClassId(request.getClassId());
        enrollment.setEnrolledAt(request.getEnrolledAt() != null ? request.getEnrolledAt() : LocalDate.now());
        enrollment.setStatus(request.getStatus() != null ? request.getStatus() : "active");
        return toResponse(enrollmentRepository.save(enrollment));
    }

    public EnrollmentResponse updateEnrollment(Integer id, EnrollmentRequest request, Integer userId) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + id));
        verifyOwnership(enrollment.getStudentId(), userId);
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

    public EnrollmentResponse updateEnrollmentStatus(Integer id, String status, Integer userId) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + id));
        verifyOwnership(enrollment.getStudentId(), userId);
        enrollment.setStatus(status);
        return toResponse(enrollmentRepository.save(enrollment));
    }

    public void deleteEnrollment(Integer id, Integer userId) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + id));
        verifyOwnership(enrollment.getStudentId(), userId);
        enrollmentRepository.deleteById(id);
    }

    private void verifyOwnership(Integer studentId, Integer userId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
        if (!student.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Access denied");
        }
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
