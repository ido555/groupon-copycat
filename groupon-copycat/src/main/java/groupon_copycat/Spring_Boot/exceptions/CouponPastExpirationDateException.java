package groupon_copycat.Spring_Boot.exceptions;

@SuppressWarnings("serial")
public class CouponPastExpirationDateException extends Exception {

    public CouponPastExpirationDateException() {
        super("the coupon you are trying to buy is past its expiration date");
    }

}
