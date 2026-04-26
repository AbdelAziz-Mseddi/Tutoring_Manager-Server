package server.service;

import server.dto.TutoringSessionRequest;
import server.dto.TutoringSessionResponse;
import server.entity.TutoringSession;
import server.repository.TutoringSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TutoringSessionService {

    @Autowired
    private TutoringSessionRepository tutoringSessionRepository;

    public Page<TutoringSessionResponse> getAllSessions(Pageable pageable) {
        return tutoringSessionRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public TutoringSessionResponse getSessionById(Integer id) {
        TutoringSession session = tutoringSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with ID: " + id));
        return toResponse(session);
    }

    public TutoringSessionResponse createSession(TutoringSessionRequest request) {
        TutoringSession session = new TutoringSession();
        session.setEnrollmentId(request.getEnrollmentId());
        session.setPaymentId(request.getPaymentId());
        session.setScheduledAt(request.getScheduledAt());
        session.setDurationMin(request.getDurationMin() != null ? request.getDurationMin() : 60);
        session.setNotes(request.getNotes());
        return toResponse(tutoringSessionRepository.save(session));
    }

    public TutoringSessionResponse updateSession(Integer id, TutoringSessionRequest request) {
        TutoringSession session = tutoringSessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with ID: " + id));
        session.setEnrollmentId(request.getEnrollmentId());
        session.setPaymentId(request.getPaymentId());
        session.setScheduledAt(request.getScheduledAt());
        if (request.getDurationMin() != null) {
            session.setDurationMin(request.getDurationMin());
        }
        session.setNotes(request.getNotes());
        return toResponse(tutoringSessionRepository.save(session));
    }

    public void deleteSession(Integer id) {
        if (!tutoringSessionRepository.existsById(id)) {
            throw new RuntimeException("Session not found with ID: " + id);
        }
        tutoringSessionRepository.deleteById(id);
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
