package server.repository;

import server.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Spring Data repository for Student entities.
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Page<Student> findAllByUser_Id(Integer userId, Pageable pageable);
}
