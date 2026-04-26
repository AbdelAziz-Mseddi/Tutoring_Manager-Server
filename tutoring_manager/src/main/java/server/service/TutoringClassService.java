package server.service;

import server.dto.TutoringClassRequest;
import server.dto.TutoringClassResponse;
import server.entity.TutoringClass;
import server.repository.TutoringClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
// Business logic for tutoring class operations and DTO mapping.
public class TutoringClassService {

    @Autowired
    private TutoringClassRepository tutoringClassRepository;

    public Page<TutoringClassResponse> getAllClasses(Pageable pageable) {
        return tutoringClassRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public TutoringClassResponse getClassById(Integer id) {
        TutoringClass tutoringClass = tutoringClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with ID: " + id));
        return toResponse(tutoringClass);
    }

    public TutoringClassResponse createClass(TutoringClassRequest request) {
        TutoringClass tutoringClass = new TutoringClass();
        tutoringClass.setName(request.getName());
        tutoringClass.setSubject(request.getSubject());
        tutoringClass.setHourlyRate(request.getHourlyRate());
        return toResponse(tutoringClassRepository.save(tutoringClass));
    }

    public TutoringClassResponse updateClass(Integer id, TutoringClassRequest request) {
        TutoringClass tutoringClass = tutoringClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with ID: " + id));
        tutoringClass.setName(request.getName());
        tutoringClass.setSubject(request.getSubject());
        tutoringClass.setHourlyRate(request.getHourlyRate());
        return toResponse(tutoringClassRepository.save(tutoringClass));
    }

    public void deleteClass(Integer id) {
        if (!tutoringClassRepository.existsById(id)) {
            throw new RuntimeException("Class not found with ID: " + id);
        }
        tutoringClassRepository.deleteById(id);
    }

    private TutoringClassResponse toResponse(TutoringClass tutoringClass) {
        return new TutoringClassResponse(
                tutoringClass.getId(),
                tutoringClass.getName(),
                tutoringClass.getSubject(),
                tutoringClass.getHourlyRate(),
                tutoringClass.getCreatedAt()
        );
    }
}
