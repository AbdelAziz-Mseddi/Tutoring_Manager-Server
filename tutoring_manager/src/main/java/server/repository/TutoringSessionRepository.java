package server.repository;

import server.entity.TutoringSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TutoringSessionRepository extends JpaRepository<TutoringSession, Integer> {
}
