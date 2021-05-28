package groupon_copycat.Spring_Boot.exceptions;

@SuppressWarnings("serial")
public class CouponDoesNotBelongToCompanyException extends Exception {
    public CouponDoesNotBelongToCompanyException() {
        super("this coupon does not belong to this company");
    }
}
