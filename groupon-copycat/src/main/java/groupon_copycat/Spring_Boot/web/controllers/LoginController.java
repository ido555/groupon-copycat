package groupon_copycat.Spring_Boot.web.controllers;

import groupon_copycat.Spring_Boot.LoginManager.LoginManager;
import groupon_copycat.Spring_Boot.beans.ClientWrapper;
import groupon_copycat.Spring_Boot.beans.Company;
import groupon_copycat.Spring_Boot.beans.Coupon;
import groupon_copycat.Spring_Boot.clientFacades.AdminFacade;
import groupon_copycat.Spring_Boot.clientFacades.ClientFacade;
import groupon_copycat.Spring_Boot.clientFacades.CompanyFacade;
import groupon_copycat.Spring_Boot.clientFacades.CustomerFacade;
import groupon_copycat.Spring_Boot.enums.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.Map.Entry;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    //	@Autowired
    public static Map<String, ClientWrapper> sessions = new HashMap<>();
    //  bypass login check
    @Autowired
    private AdminFacade specialAccess;
    @Autowired
    private LoginManager logMan;

    // O(n^2)
    @GetMapping("getAllCoupons/{token}")
    public ResponseEntity<?> getAllCoupons(@PathVariable String token) {
        if (!sessions.containsKey(token))
            return ResponseEntity.badRequest().body("Must be logged in.");
        List<Coupon> allCoupons = new ArrayList<>();
        for (Company comp : specialAccess.getAllCompanies()) {
            allCoupons.addAll(comp.getCompanyCoupons());
        }
        return ResponseEntity.ok(allCoupons);
    }


    @GetMapping("login/{clientType}/{email}/{password}")
    public ResponseEntity<String> login(@PathVariable ClientType clientType, @PathVariable String email,
                                        @PathVariable String password) {
        if (clientType == null)
            return ResponseEntity.badRequest().body("That client type is invalid");
        String token = UUID.randomUUID().toString();
        ClientFacade client = logMan.login(email, password, clientType);
        if (client != null) {
            if (isTokenized(client, clientType)) {
                return ResponseEntity.badRequest().body("Client may already be logged in");
            }
            long now = Calendar.getInstance().getTime().getTime();
            sessions.put(token, new ClientWrapper(client, now));
            System.out.println(token);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body("Login info incorrect. Check your credentials");
    }

    @DeleteMapping("logout/{token}")
    public ResponseEntity<String> logout(@PathVariable String token) {
        sessions.remove(token);
        return ResponseEntity.ok("logged out");
    }

    private boolean isTokenized(ClientFacade client, ClientType cType) {
        switch (cType) {
            case Customer:
                for (Entry<String, ClientWrapper> entry : sessions.entrySet())
                    if (entry.getValue().getClientFacade() instanceof CustomerFacade)
                        if (((CustomerFacade) entry.getValue().getClientFacade()).getCustomerId() == ((CustomerFacade) client)
                                .getCustomerId())
                            return true;
                break;
            case Company:
                for (Entry<String, ClientWrapper> entry : sessions.entrySet())
                    if (entry.getValue().getClientFacade() instanceof CompanyFacade)
                        if (((CompanyFacade) entry.getValue().getClientFacade()).getCompanyId() == ((CompanyFacade) client).getCompanyId())
                            return true;
                break;
            case Administrator:
                for (Entry<String, ClientWrapper> entry : sessions.entrySet())
                    if ((entry.getValue().getClientFacade()) instanceof AdminFacade)
                        return true;
                break;
        }
        return false;
    }
}