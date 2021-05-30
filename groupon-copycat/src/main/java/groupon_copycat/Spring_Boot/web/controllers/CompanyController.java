package groupon_copycat.Spring_Boot.web.controllers;

import groupon_copycat.Spring_Boot.beans.ClientWrapper;
import groupon_copycat.Spring_Boot.beans.Coupon;
import groupon_copycat.Spring_Boot.clientFacades.CompanyFacade;
import groupon_copycat.Spring_Boot.enums.CategoryType;
import groupon_copycat.Spring_Boot.enums.ClientType;
import groupon_copycat.Spring_Boot.exceptions.CouponDoesNotBelongToCompanyException;
import groupon_copycat.Spring_Boot.exceptions.CouponNotFoundException;
import groupon_copycat.Spring_Boot.exceptions.CouponTitleNotUniqueToYourCompanyException;
import groupon_copycat.Spring_Boot.web.AOP.ControllerCheck;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("company")
//@CrossOrigin(origins = "https://groupon-copycat-frontend.herokuapp.com/")
public class CompanyController {
    private final Map<String, ClientWrapper> sessions = LoginController.sessions;

    @ControllerCheck(cType = ClientType.Company)
    @GetMapping("allCoupons/{token}")
    public ResponseEntity<?> getCompanyCoupons(@PathVariable String token) {
        CompanyFacade comp = (CompanyFacade) sessions.get(token).getClientFacade();
        return ResponseEntity.ok(comp.getCompanyCoupons());
    }

    @ControllerCheck(cType = ClientType.Company)
    @GetMapping("categoryCoupons/{token}/{category}")
    public ResponseEntity<?> getCompanyCoupons(@PathVariable String token, @PathVariable CategoryType category) {
        CompanyFacade comp = (CompanyFacade) sessions.get(token).getClientFacade();
        try {
            return ResponseEntity.ok(comp.getCompanyCouponsByCategory(category));
        } catch (CouponNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ControllerCheck(cType = ClientType.Company)
    @GetMapping("priceCoupons/{token}/{maxPrice}")
    public ResponseEntity<?> getCompanyCoupons(@PathVariable String token, @PathVariable Double maxPrice) {
        CompanyFacade comp = (CompanyFacade) sessions.get(token).getClientFacade();
        try {
            return ResponseEntity.ok(comp.getCompanyCouponsUnderPrice(maxPrice));
        } catch (CouponNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ControllerCheck(cType = ClientType.Company)
    @GetMapping("details/{token}")
    public ResponseEntity<?> getCompanyDetails(@PathVariable String token) {
        CompanyFacade comp = (CompanyFacade) sessions.get(token).getClientFacade();
        return ResponseEntity.ok(comp.getCompanyDetails());
    }

    @ControllerCheck(cType = ClientType.Company)
    @PostMapping("addCoupon/{token}")
    public ResponseEntity<?> addCoupon(@PathVariable String token, @RequestBody Coupon coupon) {
        CompanyFacade comp = (CompanyFacade) sessions.get(token).getClientFacade();
        try {
            comp.addCoupon(coupon);
            return ResponseEntity.ok("Coupon added successfully");
        } catch (CouponTitleNotUniqueToYourCompanyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ControllerCheck(cType = ClientType.Company)
    @PutMapping("updateCoupon/{token}")
    public ResponseEntity<?> updateCoupon(@PathVariable String token, @RequestBody Coupon coupon) {
        CompanyFacade comp = (CompanyFacade) sessions.get(token).getClientFacade();
        coupon.setCompany(comp.getCompanyDetails());
        try {
            comp.updateCoupon(coupon);
            return ResponseEntity.ok("Coupon updated successfully");
        } catch (CouponDoesNotBelongToCompanyException | CouponTitleNotUniqueToYourCompanyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ControllerCheck(cType = ClientType.Company)
    @DeleteMapping("deleteCoupon/{token}/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable String token, @PathVariable int id) {
        CompanyFacade comp = (CompanyFacade) sessions.get(token).getClientFacade();
        try {
            comp.deleteCoupon(id);
            return ResponseEntity.ok("Coupon deleted successfully");
        } catch (CouponDoesNotBelongToCompanyException | CouponNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
