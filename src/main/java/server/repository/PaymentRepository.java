package server.repository;

import server.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// Spring Data repository for Payment entities.
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Page<Payment> findAllByUser_Id(Integer userId, Pageable pageable);
}
