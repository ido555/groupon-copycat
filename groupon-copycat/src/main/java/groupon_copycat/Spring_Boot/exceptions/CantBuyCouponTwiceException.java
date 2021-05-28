package groupon_copycat.Spring_Boot.exceptions;

@SuppressWarnings("serial")
public class CantBuyCouponTwiceException extends Exception {
    public CantBuyCouponTwiceException() {
        super("you already bought this coupon once");
    }
}
