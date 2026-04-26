package server.repository;

import server.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Spring Data repository for Student entities.
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
