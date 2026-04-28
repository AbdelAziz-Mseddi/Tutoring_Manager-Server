package server.repository;

import server.entity.TutoringClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Spring Data repository for TutoringClass entities.
public interface TutoringClassRepository extends JpaRepository<TutoringClass, Integer> {
    Page<TutoringClass> findAllByUser_Id(Integer userId, Pageable pageable);
}
