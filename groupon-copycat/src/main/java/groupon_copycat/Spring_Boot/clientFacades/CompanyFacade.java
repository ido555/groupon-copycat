package groupon_copycat.Spring_Boot.clientFacades;

import groupon_copycat.Spring_Boot.beans.Company;
import groupon_copycat.Spring_Boot.beans.Coupon;
import groupon_copycat.Spring_Boot.enums.CategoryType;
import groupon_copycat.Spring_Boot.exceptions.CouponDoesNotBelongToCompanyException;
import groupon_copycat.Spring_Boot.exceptions.CouponNotFoundException;
import groupon_copycat.Spring_Boot.exceptions.CouponTitleNotUniqueToYourCompanyException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Scope("prototype")
public class CompanyFacade extends ClientFacade {
    private int companyId = 0;

    public boolean login(String email, String password) {
        if (companyRepo.existsByEmailAndPassword(email, password)) {
            companyId = companyRepo.findByEmailAndPassword(email, password).getCompanyId();
            return true;
        }
        return false;
    }

    public void addCoupon(Coupon couponToAdd) throws CouponTitleNotUniqueToYourCompanyException {
        // set Coupon company the company adding it.
        Company compToAddCouponTo = companyRepo.getOne(companyId);
        couponToAdd.setCompany(compToAddCouponTo);
        // check if coupon title is unique within company
        for (Coupon coupon : companyRepo.getOne(companyId).getCompanyCoupons())
            if (coupon.getTitle().equalsIgnoreCase(couponToAdd.getTitle()))
                throw new CouponTitleNotUniqueToYourCompanyException();
        couponRepo.save(couponToAdd);
    }

    public void updateCoupon(Coupon couponToUpdate)
            throws CouponDoesNotBelongToCompanyException, CouponTitleNotUniqueToYourCompanyException {
        // make sure this company is updating only ITS coupon
        Company company = couponToUpdate.getCompany();
        companyRepo.getOne(companyId);
        if (company.getCompanyId() != companyId) {
            throw new CouponDoesNotBelongToCompanyException();
        }
        // check if coupon title is unique within company
        for (Coupon coupon : companyRepo.getOne(companyId).getCompanyCoupons())
            if (coupon.getCouponId() != couponToUpdate.getCouponId())
                if (coupon.getTitle().equalsIgnoreCase(couponToUpdate.getTitle()))
                    throw new CouponTitleNotUniqueToYourCompanyException();
        couponRepo.save(couponToUpdate);
    }

    public void deleteCoupon(int couponId) throws CouponDoesNotBelongToCompanyException, CouponNotFoundException {
        // check if coupon id is correct
        couponRepo.findById(couponId).orElseThrow(CouponNotFoundException::new);
        // make sure this company is updating only ITS coupon
        if (couponRepo.getOne(couponId).getCompany().getCompanyId() != companyId)
            throw new CouponDoesNotBelongToCompanyException();
        couponRepo.deleteCouponPurchasesByCouponId(couponId);
        couponRepo.delete(couponRepo.getOne(couponId));
    }

    public List<Coupon> getCompanyCoupons() {
        return couponRepo.findCouponsBycompany(companyRepo.getOne(companyId));
    }

    public ArrayList<Coupon> getCompanyCouponsByCategory(CategoryType categoryType) throws CouponNotFoundException {
        ArrayList<Coupon> coupons = couponRepo.findCouponsBycompany(companyRepo.getOne(companyId));
        ArrayList<Coupon> filteredCoupons = new ArrayList<>();
        for (Coupon coupon : coupons)
            if (coupon.getCategory() == categoryType)
                filteredCoupons.add(coupon);
        if (filteredCoupons.isEmpty())
            throw new CouponNotFoundException();
        return filteredCoupons;
    }

    public ArrayList<Coupon> getCompanyCouponsUnderPrice(double price) throws CouponNotFoundException {
        ArrayList<Coupon> coupons = couponRepo.findCouponsBycompany(companyRepo.getOne(companyId));
        ArrayList<Coupon> filteredCoupons = new ArrayList<>();
        for (Coupon coupon : coupons) {
            if (coupon.getPrice() <= price)
                filteredCoupons.add(coupon);
            if (filteredCoupons.isEmpty())
                throw new CouponNotFoundException();
        }
        return filteredCoupons;
    }

    public int getCompanyId() {
        return companyId;
    }

    public Company getCompanyDetails() {
        return companyRepo.findById(companyId).orElse(null);
    }
}