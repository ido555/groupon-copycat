package groupon_copycat.Spring_Boot.clientFacades;

import groupon_copycat.Spring_Boot.beans.Company;
import groupon_copycat.Spring_Boot.beans.Coupon;
import groupon_copycat.Spring_Boot.beans.Customer;
import groupon_copycat.Spring_Boot.enums.CategoryType;
import groupon_copycat.Spring_Boot.exceptions.ClientNotFoundException;
import groupon_copycat.Spring_Boot.exceptions.EmailAlreadyInUseException;
import groupon_copycat.Spring_Boot.exceptions.NameException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
public class AdminFacade extends ClientFacade {

    public boolean login(String email, String password) {
//        addUsers();
        return email.equals("admin@admin.com") && password.equals("admin");
    }

    public void addCompany(Company newCompany) throws NameException, EmailAlreadyInUseException {
        // check if newCompany's name is already in use
        if (companyRepo.existsByName(newCompany.getName()))
            throw new NameException();
        // check if newCompany's email is already in use
        if (companyRepo.existsByEmail(newCompany.getEmail()))
            throw new EmailAlreadyInUseException();
        companyRepo.save(newCompany);
    }

    public void updateCompany(Company updatedCompany) throws EmailAlreadyInUseException, NameException {
        // check if email already belongs to another company while skipping self
        if (companyRepo.existsByEmailAndCompanyIdNot(updatedCompany.getEmail(), updatedCompany.getCompanyId()))
            throw new EmailAlreadyInUseException();
        // check if name was changed in comparison with DB company
        if (!companyRepo.getOne(updatedCompany.getCompanyId()).getName().equals(updatedCompany.getName()))
            throw new NameException();
        companyRepo.save(updatedCompany);
    }

    public void deleteCompany(Company company) throws ClientNotFoundException {
        if (!companyRepo.existsByEmailAndPassword(company.getPassword(), company.getEmail()))
            throw new ClientNotFoundException();
        // delete customer coupon purchases for these coupons
        for (Coupon coupon : companyRepo.getOne((company.getCompanyId())).getCompanyCoupons()) {
            couponRepo.deleteCouponPurchasesByCouponId(coupon.getCouponId()); // delete coupon purchases
        }
        // delete all company coupons then delete the company itself
        couponRepo.deleteInBatch(company.getCompanyCoupons());
        companyRepo.delete(company); // delete the company itself
    }

    public void deleteCompany(int id) throws ClientNotFoundException {
        companyRepo.findById(id).orElseThrow(ClientNotFoundException::new);
        // delete customer coupon purchases for these coupons
        for (Coupon coupon : (companyRepo.findById(id).orElseThrow(ClientNotFoundException::new)).getCompanyCoupons()) {
            couponRepo.deleteCouponPurchasesByCouponId(coupon.getCouponId()); // delete coupon purchases
        }
        // delete all company coupons then delete the company itself
        couponRepo.deleteInBatch(companyRepo.getOne(id).getCompanyCoupons());
        companyRepo.deleteById(id); // delete the company itself
    }

    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    public Company getOneCompany(int companyId) throws ClientNotFoundException {
        return companyRepo.findById(companyId).orElseThrow(ClientNotFoundException::new);
    }

    public void addCustomer(Customer newCustomer) throws EmailAlreadyInUseException {
        if (customerRepo.existsByEmail(newCustomer.getEmail()))
            throw new EmailAlreadyInUseException();
        customerRepo.save(newCustomer);
    }

    public void updateCustomer(Customer customer) throws EmailAlreadyInUseException {
        if (customerRepo.existsByEmailAndCustomerIdNot(customer.getEmail(), customer.getCustomerId()))
            throw new EmailAlreadyInUseException();
        customerRepo.save(customer);
    }

    public void deleteCustomer(Customer customer) throws ClientNotFoundException {
        if (!customerRepo.existsByEmailAndPassword(customer.getPassword(), customer.getEmail()))
            throw new ClientNotFoundException();
        couponRepo.deleteCouponPurchasesByCustomerId(customer.getCustomerId());
        customerRepo.delete(customer);
    }

    public void deleteCustomer(int id) throws ClientNotFoundException {
        customerRepo.findById(id).orElseThrow(ClientNotFoundException::new);
        couponRepo.deleteCouponPurchasesByCustomerId(id);
        customerRepo.deleteById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public Customer getOneCustomer(int customerId) throws ClientNotFoundException {
        return customerRepo.findById(customerId).orElseThrow(ClientNotFoundException::new);
    }

    public void addUsers() {
        Random rand = new Random();
		LocalDate now = LocalDate.now();
		LocalDate later = LocalDate.now();
        for (int i = 0; i < 100; i++) {
            later = later.plusDays(1);
            Customer c = new Customer("name" + i, "last" + i, "e" + i + "@e.com",
                    "1");
            Company co = new Company("company" + i, "c" + i + "@c.com", "1");
            for (int j = 0; j < rand.nextInt(6); j++) {
			Coupon cou = new Coupon(rand.nextInt(900)+100, round((rand.nextDouble()+0.1)*200),
                    "coupon " + i + "-" + j, "lorem ipsum dolor sit amet " + i + "-" + j,
					"path/img" + i + "-" + j + ".jpg", Date.valueOf(now), Date.valueOf(later) ,
                    CategoryType.values()[rand.nextInt(5)]);
                    cou.setCompany(co);
                    companyRepo.save(co);
                    couponRepo.save(cou);
            }
            companyRepo.save(co);
            customerRepo.save(c);
        }
    }
    private  double round(double value) {
        double scale = Math.pow(10, 2);
        return Math.round(value * scale) / scale;
    }
}
