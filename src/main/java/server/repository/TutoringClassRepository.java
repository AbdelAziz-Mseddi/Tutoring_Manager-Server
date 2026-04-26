package server.repository;

import server.entity.TutoringClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Spring Data repository for TutoringClass entities.
public interface TutoringClassRepository extends JpaRepository<TutoringClass, Integer> {
}
