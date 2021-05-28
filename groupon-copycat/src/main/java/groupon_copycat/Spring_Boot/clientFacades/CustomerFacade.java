package groupon_copycat.Spring_Boot.clientFacades;

import groupon_copycat.Spring_Boot.beans.Coupon;
import groupon_copycat.Spring_Boot.beans.Customer;
import groupon_copycat.Spring_Boot.enums.CategoryType;
import groupon_copycat.Spring_Boot.exceptions.CantBuyCouponTwiceException;
import groupon_copycat.Spring_Boot.exceptions.CouponNotFoundException;
import groupon_copycat.Spring_Boot.exceptions.CouponOutOfStockException;
import groupon_copycat.Spring_Boot.exceptions.CouponPastExpirationDateException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

@Service
@Scope("prototype")
@Transactional
public class CustomerFacade extends ClientFacade {
    private int customerId = 0;

    public boolean login(String email, String password) {
        if (customerRepo.existsByEmailAndPassword(email, password)) {
            customerId = customerRepo.findByEmailAndPassword(email, password).getCustomerId();
            return true;
        }
        return false;
    }

    public void purchaseCoupon(Coupon couponToBuy) throws CantBuyCouponTwiceException, CouponNotFoundException,
            CouponOutOfStockException, CouponPastExpirationDateException {
        // check if the couponToBuy exists
        couponToBuy = couponRepo.findById(couponToBuy.getCouponId()).orElseThrow(CouponNotFoundException::new);
        // check if couponToBuy is in stock
        if (couponToBuy.getAmount() < 1)
            throw new CouponOutOfStockException();
        Set<Coupon> customerCoupons = getCustomerCoupons();
        // check if the customer hasn't already bought this couponToBuy
        if (customerCoupons.contains(couponToBuy))
            throw new CantBuyCouponTwiceException();
        // check if couponToBuy is past its expiration date
        Date now = new Date(Calendar.getInstance().getTimeInMillis());
        if (couponToBuy.getEndDate().before(now))
            throw new CouponPastExpirationDateException();
        Customer customer = customerRepo.getOne(customerId);
        customer.buyCoupon(couponToBuy);
        couponToBuy.setAmount(couponToBuy.getAmount() - 1);
        customerRepo.save(customer);
        couponRepo.save(couponToBuy);
    }

    public Set<Coupon> getCustomerCoupons() {
        return customerRepo.findById(customerId).get().getCoupons();
    }

    public Set<Coupon> getCustomerCouponsByCategory(CategoryType category) throws CouponNotFoundException {
        Set<Coupon> filteredCoupons = new HashSet<>();
        for (Coupon coupon : getCustomerCoupons())
            if (coupon.getCategory() == category)
                filteredCoupons.add(coupon);
        if (filteredCoupons.isEmpty())
            throw new CouponNotFoundException();
        return filteredCoupons;
    }

    public Set<Coupon> getCustomerCouponsUnderPrice(double maxPrice) throws CouponNotFoundException {
        Set<Coupon> filteredCoupons = new HashSet<>();
        for (Coupon coupon : getCustomerCoupons())
            if (coupon.getPrice() < maxPrice)
                filteredCoupons.add(coupon);
        if (filteredCoupons.isEmpty())
            throw new CouponNotFoundException();
        return filteredCoupons;
    }

    public Customer getCustomerDetails() {
        return customerRepo.findById(customerId).orElse(null);
    }

    public int getCustomerId() {
        return customerId;
    }
}
