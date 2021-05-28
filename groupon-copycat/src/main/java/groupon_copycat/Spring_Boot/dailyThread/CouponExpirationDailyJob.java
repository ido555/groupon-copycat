package groupon_copycat.Spring_Boot.dailyThread;

import groupon_copycat.Spring_Boot.beans.Coupon;
import groupon_copycat.Spring_Boot.repos.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

// a Component is a Singleton by default
@Component
public class CouponExpirationDailyJob extends Thread {
    private boolean quit = false;
    @Autowired
    private CouponRepo couponRepo;

    @Override
    public void run() {
        try {
            while (!(quit)) {
                Date now = new Date(Calendar.getInstance().getTimeInMillis());
                for (Coupon coupon : couponRepo.findAll()) {
                    if (coupon.getEndDate().before(now)) {
                        System.out.println("deleting coupon past expiration date " + coupon);
                        couponRepo.deleteCouponPurchasesByCouponId(coupon.getCouponId());
                        couponRepo.deleteById(coupon.getCouponId());
                    }
                }
                // 24 hours in milliseconds
                sleep(TimeUnit.HOURS.toMillis(24));
            }
        } catch (InterruptedException e) {
            return;
        }
    }

    public void stopJob() {
        quit = true;
        interrupt();
    }
}
