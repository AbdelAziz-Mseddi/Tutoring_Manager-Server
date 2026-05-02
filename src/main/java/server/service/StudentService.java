package server.service;

import server.dto.StudentRequest;
import server.dto.StudentResponse;
import server.entity.Student;
import server.entity.User;
import server.exception.ForbiddenException;
import server.repository.StudentRepository;
import server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
// Business logic for student operations and DTO mapping.
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<StudentResponse> getAllStudents(Integer userId, Pageable pageable) {
        return studentRepository.findAllByUser_Id(userId, pageable)
                .map(this::toResponse);
    }

    public StudentResponse getStudentById(Integer id, Integer userId) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        verifyOwnership(student.getUser().getId(), userId);
        return toResponse(student);
    }

    public StudentResponse createStudent(StudentRequest request, Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        Student student = new Student();
        student.setUser(user);
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setPhone(request.getPhone());
        student.setEmail(request.getEmail());
        return toResponse(studentRepository.save(student));
    }

    public StudentResponse updateStudent(Integer id, StudentRequest request, Integer userId) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        verifyOwnership(student.getUser().getId(), userId);
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setPhone(request.getPhone());
        student.setEmail(request.getEmail());
        return toResponse(studentRepository.save(student));
    }

    public void deleteStudent(Integer id, Integer userId) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        verifyOwnership(student.getUser().getId(), userId);
        studentRepository.deleteById(id);
    }

    private void verifyOwnership(Integer resourceUserId, Integer authenticatedUserId) {
        if (!resourceUserId.equals(authenticatedUserId)) {
            throw new ForbiddenException("Access denied");
        }
    }

    private StudentResponse toResponse(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getUser().getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getPhone(),
                student.getEmail(),
                student.getCreatedAt()
        );
    }
}
