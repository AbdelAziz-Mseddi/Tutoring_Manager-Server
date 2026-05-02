package server.repository;

import server.entity.Enrollment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Spring Data repository for Enrollment entities.
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    Page<Enrollment> findAllByStudent_User_Id(Integer userId, Pageable pageable);
}
