package server.repository;

import server.entity.TutoringSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Spring Data repository for TutoringSession entities.
public interface TutoringSessionRepository extends JpaRepository<TutoringSession, Integer> {
    Page<TutoringSession> findAllByEnrollment_Student_User_Id(Integer userId, Pageable pageable);
}
