package groupon_copycat.Spring_Boot.clientFacades;

import groupon_copycat.Spring_Boot.repos.CompanyRepo;
import groupon_copycat.Spring_Boot.repos.CouponRepo;
import groupon_copycat.Spring_Boot.repos.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ClientFacade {
    @Autowired
    protected CompanyRepo companyRepo;
    @Autowired
    protected CouponRepo couponRepo;
    @Autowired
    protected CustomerRepo customerRepo;

    public abstract boolean login(String email, String password);
}
