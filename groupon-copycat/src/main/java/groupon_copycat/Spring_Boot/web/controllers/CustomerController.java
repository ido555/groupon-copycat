package groupon_copycat.Spring_Boot.web.controllers;

import groupon_copycat.Spring_Boot.beans.ClientWrapper;
import groupon_copycat.Spring_Boot.beans.Coupon;
import groupon_copycat.Spring_Boot.clientFacades.CustomerFacade;
import groupon_copycat.Spring_Boot.enums.CategoryType;
import groupon_copycat.Spring_Boot.enums.ClientType;
import groupon_copycat.Spring_Boot.exceptions.CantBuyCouponTwiceException;
import groupon_copycat.Spring_Boot.exceptions.CouponNotFoundException;
import groupon_copycat.Spring_Boot.exceptions.CouponOutOfStockException;
import groupon_copycat.Spring_Boot.exceptions.CouponPastExpirationDateException;
import groupon_copycat.Spring_Boot.web.AOP.ControllerCheck;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins = "https://groupon-copycat.herokuapp.com:4200")
public class CustomerController {
    //	@Autowired
    private final Map<String, ClientWrapper> sessions = LoginController.sessions;

    @ControllerCheck(cType = ClientType.Customer)
    @GetMapping("details/{token}")
    public ResponseEntity<?> getCustomerDetails(@PathVariable String token) {
        CustomerFacade cust = (CustomerFacade) sessions.get(token).getClientFacade();
        return ResponseEntity.ok(cust.getCustomerDetails());
    }

    @ControllerCheck(cType = ClientType.Customer)
    @GetMapping("categoryCoupons/{token}/{category}")
    public ResponseEntity<?> getCustomerCoupons(@PathVariable String token, @PathVariable CategoryType category) {
        CustomerFacade cust = (CustomerFacade) sessions.get(token).getClientFacade();
        try {
            return ResponseEntity.ok(cust.getCustomerCouponsByCategory(category));
        } catch (CouponNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ControllerCheck(cType = ClientType.Customer)
    @GetMapping("priceCoupons/{token}/{maxPrice}")
    public ResponseEntity<?> getCustomerCoupons(@PathVariable String token, @PathVariable Double maxPrice) {
        CustomerFacade cust = (CustomerFacade) sessions.get(token).getClientFacade();
        try {
            return ResponseEntity.ok(cust.getCustomerCouponsUnderPrice(maxPrice));
        } catch (CouponNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ControllerCheck(cType = ClientType.Customer)
    @GetMapping("allCoupons/{token}")
    public ResponseEntity<?> getCustomerCoupons(@PathVariable String token) {
        CustomerFacade cust = (CustomerFacade) sessions.get(token).getClientFacade();
        return ResponseEntity.ok(cust.getCustomerCoupons());
    }

    @ControllerCheck(cType = ClientType.Customer)
    @PostMapping("buyCoupon/{token}")
    public ResponseEntity<?> purchaseCoupon(@PathVariable String token, @RequestBody Coupon coupon) {
        CustomerFacade cust = (CustomerFacade) sessions.get(token).getClientFacade();
        try {
            cust.purchaseCoupon(coupon);
        } catch (CantBuyCouponTwiceException | CouponNotFoundException | CouponPastExpirationDateException | CouponOutOfStockException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Coupon purchased successfully");
    }
}