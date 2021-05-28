package groupon_copycat.Spring_Boot.web.controllers;

import groupon_copycat.Spring_Boot.beans.ClientWrapper;
import groupon_copycat.Spring_Boot.beans.Company;
import groupon_copycat.Spring_Boot.beans.Customer;
import groupon_copycat.Spring_Boot.clientFacades.AdminFacade;
import groupon_copycat.Spring_Boot.enums.ClientType;
import groupon_copycat.Spring_Boot.exceptions.ClientNotFoundException;
import groupon_copycat.Spring_Boot.exceptions.EmailAlreadyInUseException;
import groupon_copycat.Spring_Boot.exceptions.NameException;
import groupon_copycat.Spring_Boot.web.AOP.ControllerCheck;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "https://groupon-copycat.herokuapp.com:4200")
public class AdminController {
    // ended up using sessions to get the Admin Facade instead of autowire
    // to keep the code robust in case of future implementation of multiple admins
//    @Autowired
//    private AdminFacade admin;
    private final Map<String, ClientWrapper> sessions = LoginController.sessions;

    @ControllerCheck(cType = ClientType.Administrator)
    @PostMapping("add/company/{token}")
    public ResponseEntity<?> addCompany(@PathVariable String token, @RequestBody Company company) {
        try {
            AdminFacade admin = (AdminFacade) sessions.get(token).getClientFacade();
            admin.addCompany(company);
            return ResponseEntity.ok("company added succesfully");
        } catch (NameException | EmailAlreadyInUseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ControllerCheck(cType = ClientType.Administrator)
    @PutMapping("update/company/{token}")
    public ResponseEntity<?> updateCompany(@PathVariable String token, @RequestBody Company company) {
        try {
            AdminFacade admin = (AdminFacade) sessions.get(token).getClientFacade();
            admin.updateCompany(company);
            return ResponseEntity.ok("company updated succesfully");
        } catch (EmailAlreadyInUseException | NameException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ControllerCheck(cType = ClientType.Administrator)
    @DeleteMapping("delete/company/{token}/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable String token, @PathVariable int id) {
        try {
            AdminFacade admin = (AdminFacade) sessions.get(token).getClientFacade();
            admin.deleteCompany(id);
            return ResponseEntity.ok("company deleted succesfully");
        } catch (ClientNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ControllerCheck(cType = ClientType.Administrator)
    @GetMapping("getAll/companies/{token}")
    public ResponseEntity<List<Company>> getAllCompanies(@PathVariable String token) {
        AdminFacade admin = (AdminFacade) sessions.get(token).getClientFacade();
        return ResponseEntity.ok(admin.getAllCompanies());
    }

    @ControllerCheck(cType = ClientType.Administrator)
    @GetMapping("get/company/{token}/{id}")
    public ResponseEntity<?> getOneCompany(@PathVariable String token, @PathVariable int id) {
        try {
            AdminFacade admin = (AdminFacade) sessions.get(token).getClientFacade();
            Company comp = admin.getOneCompany(id);
            return ResponseEntity.ok(comp);
        } catch (ClientNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ControllerCheck(cType = ClientType.Administrator)
    @PostMapping("add/customer/{token}")
    public ResponseEntity<?> addCustomer(@PathVariable String token, @RequestBody Customer customer) {
        try {
            AdminFacade admin = (AdminFacade) sessions.get(token).getClientFacade();
            admin.addCustomer(customer);
            return ResponseEntity.ok("user succesfully added");
        } catch (EmailAlreadyInUseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ControllerCheck(cType = ClientType.Administrator)
    @PutMapping("update/customer/{token}")
    public ResponseEntity<?> updateCustomer(@PathVariable String token, @RequestBody Customer customer) {
        try {
            AdminFacade admin = (AdminFacade) sessions.get(token).getClientFacade();
            admin.updateCustomer(customer);
            return ResponseEntity.ok("user succesfully updated");
        } catch (EmailAlreadyInUseException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ControllerCheck(cType = ClientType.Administrator)
    @DeleteMapping("delete/customer/{token}/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String token, @PathVariable int id) {
        try {
            AdminFacade admin = (AdminFacade) sessions.get(token).getClientFacade();
            admin.deleteCustomer(id);
            return ResponseEntity.ok("user succesfully deleted");
        } catch (ClientNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ControllerCheck(cType = ClientType.Administrator)
    @GetMapping("getAll/customers/{token}")
    public ResponseEntity<?> getAllCustomers(@PathVariable String token) {
        AdminFacade admin = (AdminFacade) sessions.get(token).getClientFacade();
        return ResponseEntity.ok(admin.getAllCustomers());
    }

    @ControllerCheck(cType = ClientType.Administrator)
    @GetMapping("get/customer/{token}/{id}")
    public ResponseEntity<?> getOneCustomer(@PathVariable String token, @PathVariable int id) {
        try {
            AdminFacade admin = (AdminFacade) sessions.get(token).getClientFacade();
            return ResponseEntity.ok(admin.getOneCustomer(id));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("addUsersAndCompaniesDANGER/{token}")
    public void addThem(@PathVariable String token) {
        AdminFacade admin = (AdminFacade) sessions.get(token).getClientFacade();
        admin.addUsers();
    }

}
