package server.service;

import server.dto.TutoringClassRequest;
import server.dto.TutoringClassResponse;
import server.entity.TutoringClass;
import server.entity.User;
import server.repository.TutoringClassRepository;
import server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
// Business logic for tutoring class operations and DTO mapping.
public class TutoringClassService {

    @Autowired
    private TutoringClassRepository tutoringClassRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<TutoringClassResponse> getAllClasses(Integer userId, Pageable pageable) {
        return tutoringClassRepository.findAllByUser_Id(userId, pageable)
                .map(this::toResponse);
    }

    public TutoringClassResponse getClassById(Integer id) {
        TutoringClass tutoringClass = tutoringClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with ID: " + id));
        return toResponse(tutoringClass);
    }

    public TutoringClassResponse createClass(TutoringClassRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));
        TutoringClass tutoringClass = new TutoringClass();
        tutoringClass.setUser(user);
        tutoringClass.setName(request.getName());
        tutoringClass.setSubject(request.getSubject());
        tutoringClass.setHourlyRate(request.getHourlyRate());
        return toResponse(tutoringClassRepository.save(tutoringClass));
    }

    public TutoringClassResponse updateClass(Integer id, TutoringClassRequest request) {
        TutoringClass tutoringClass = tutoringClassRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Class not found with ID: " + id));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));
        tutoringClass.setUser(user);
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
                tutoringClass.getUser().getId(),
                tutoringClass.getName(),
                tutoringClass.getSubject(),
                tutoringClass.getHourlyRate(),
                tutoringClass.getCreatedAt()
        );
    }
}
