package groupon_copycat.Spring_Boot.repos;

import groupon_copycat.Spring_Boot.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    boolean existsByEmailAndPassword(String email, String password);

    Customer findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    boolean existsByEmailAndCustomerIdNot(String email, int customerId);
}
