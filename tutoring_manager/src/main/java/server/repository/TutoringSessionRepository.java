package server.repository;

import server.entity.TutoringSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Spring Data repository for TutoringSession entities.
public interface TutoringSessionRepository extends JpaRepository<TutoringSession, Integer> {
}
