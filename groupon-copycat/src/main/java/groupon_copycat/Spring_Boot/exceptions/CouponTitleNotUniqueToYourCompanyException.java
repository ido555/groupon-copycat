package groupon_copycat.Spring_Boot.exceptions;

@SuppressWarnings("serial")
public class CouponTitleNotUniqueToYourCompanyException extends Exception {

    public CouponTitleNotUniqueToYourCompanyException() {
        super("coupon name must be unique within your company");
    }
}
