package groupon_copycat.Spring_Boot.repos;

import groupon_copycat.Spring_Boot.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CompanyRepo extends JpaRepository<Company, Integer> {
    Company findByEmailAndPassword(String email, String password);

    boolean existsByEmailAndPassword(String email, String password);

    boolean existsByEmailAndCompanyIdNot(String email, int companyId);

    boolean existsByEmail(String email);

    boolean existsByName(String name);
}
