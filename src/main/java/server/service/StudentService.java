package server.service;

import server.dto.StudentRequest;
import server.dto.StudentResponse;
import server.entity.Student;
import server.entity.User;
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

    public StudentResponse getStudentById(Integer id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        return toResponse(student);
    }

    public StudentResponse createStudent(StudentRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));
        Student student = new Student();
        student.setUser(user);
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setPhone(request.getPhone());
        student.setEmail(request.getEmail());
        return toResponse(studentRepository.save(student));
    }

    public StudentResponse updateStudent(Integer id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + request.getUserId()));
        student.setUser(user);
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setPhone(request.getPhone());
        student.setEmail(request.getEmail());
        return toResponse(studentRepository.save(student));
    }

    public void deleteStudent(Integer id) {
        if (!studentRepository.existsById(id)) {
            throw new RuntimeException("Student not found with ID: " + id);
        }
        studentRepository.deleteById(id);
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
