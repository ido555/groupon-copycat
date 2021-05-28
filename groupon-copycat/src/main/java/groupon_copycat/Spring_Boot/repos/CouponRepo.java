package groupon_copycat.Spring_Boot.repos;

import groupon_copycat.Spring_Boot.beans.Company;
import groupon_copycat.Spring_Boot.beans.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Transactional
public interface CouponRepo extends JpaRepository<Coupon, Integer> {
    // deletes all matching coupons from the ManyToMany relationship in the DB (instead of having to iterate over objects in java)
    @Modifying
    @Query(nativeQuery = true, value =
            "DELETE FROM grouponcopycat.customers_coupons WHERE grouponcopycat.customers_coupons.coupon_id = :couponId")
    void deleteCouponPurchasesByCouponId(@Param("couponId") Integer couponId);

    // deletes all matching coupons from the ManyToMany relationship in the DB (instead of having to iterate over objects in java)
    @Modifying
    @Query(nativeQuery = true, value =
            "DELETE FROM grouponcopycat.customers_coupons WHERE grouponcopycat.customers_coupons.customer_id = :customerId")
    void deleteCouponPurchasesByCustomerId(@Param("customerId") Integer customerId);

    ArrayList<Coupon> findCouponsBycompany(Company company);
}
