package server.service;

import server.dto.TutoringSessionRequest;
import server.dto.TutoringSessionResponse;
import server.entity.Enrollment;
import server.entity.Student;
import server.exception.ForbiddenException;
import server.repository.EnrollmentRepository;
import server.repository.StudentRepository;
import server.repository.TutoringSessionRepository;
import server.entity.TutoringSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
// Business logic for tutoring session operations and DTO mapping.
public class TutoringSessionService {

    @Autowired
    private TutoringSessionRepository tutoringSessionRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private StudentRepository studentRepository;

    public Page<TutoringSessionResponse> getAllSessions(Integer userId, Pageable pageable) {
        return tutoringSessionRepository.findAllByEnrollment_Student_User_Id(userId, pageable)
                .map(this::toResponse);
    }

    public TutoringSessionResponse getSessionById(Integer id, Integer userId) {
        TutoringSession session = tutoringSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with ID: " + id));
        verifyOwnership(session.getEnrollmentId(), userId);
        return toResponse(session);
    }

    public TutoringSessionResponse createSession(TutoringSessionRequest request, Integer userId) {
        verifyOwnership(request.getEnrollmentId(), userId);
        TutoringSession session = new TutoringSession();
        session.setEnrollmentId(request.getEnrollmentId());
        session.setPaymentId(request.getPaymentId());
        session.setScheduledAt(request.getScheduledAt());
        session.setDurationMin(request.getDurationMin() != null ? request.getDurationMin() : 60);
        session.setNotes(request.getNotes());
        return toResponse(tutoringSessionRepository.save(session));
    }

    public TutoringSessionResponse updateSession(Integer id, TutoringSessionRequest request, Integer userId) {
        TutoringSession session = tutoringSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with ID: " + id));
        verifyOwnership(session.getEnrollmentId(), userId);
        session.setEnrollmentId(request.getEnrollmentId());
        session.setPaymentId(request.getPaymentId());
        session.setScheduledAt(request.getScheduledAt());
        if (request.getDurationMin() != null) {
            session.setDurationMin(request.getDurationMin());
        }
        session.setNotes(request.getNotes());
        return toResponse(tutoringSessionRepository.save(session));
    }

    public void deleteSession(Integer id, Integer userId) {
        TutoringSession session = tutoringSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with ID: " + id));
        verifyOwnership(session.getEnrollmentId(), userId);
        tutoringSessionRepository.deleteById(id);
    }

    private void verifyOwnership(Integer enrollmentId, Integer userId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Enrollment not found with ID: " + enrollmentId));
        Student student = studentRepository.findById(enrollment.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + enrollment.getStudentId()));
        if (!student.getUser().getId().equals(userId)) {
            throw new ForbiddenException("Access denied");
        }
    }

    private TutoringSessionResponse toResponse(TutoringSession session) {
        return new TutoringSessionResponse(
                session.getId(),
                session.getEnrollmentId(),
                session.getPaymentId(),
                session.getScheduledAt(),
                session.getDurationMin(),
                session.getNotes(),
                session.getCreatedAt()
        );
    }
}
