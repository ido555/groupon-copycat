package groupon_copycat.Spring_Boot.exceptions;

@SuppressWarnings("serial")
public class CouponOutOfStockException extends Exception {
    public CouponOutOfStockException() {
        super("coupon out of stock");
    }
}
