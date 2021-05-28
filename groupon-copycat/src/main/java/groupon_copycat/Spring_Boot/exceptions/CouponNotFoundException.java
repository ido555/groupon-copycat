package groupon_copycat.Spring_Boot.exceptions;

@SuppressWarnings("serial")
public class CouponNotFoundException extends Exception {
    public CouponNotFoundException() {
        super("no (matching) Coupon(s) found");
    }
}
