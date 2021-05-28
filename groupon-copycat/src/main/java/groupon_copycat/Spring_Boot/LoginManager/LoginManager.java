package groupon_copycat.Spring_Boot.LoginManager;

import groupon_copycat.Spring_Boot.clientFacades.AdminFacade;
import groupon_copycat.Spring_Boot.clientFacades.ClientFacade;
import groupon_copycat.Spring_Boot.clientFacades.CompanyFacade;
import groupon_copycat.Spring_Boot.clientFacades.CustomerFacade;
import groupon_copycat.Spring_Boot.enums.ClientType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

//a Component is a Singleton by default
@Component
@Transactional
public class LoginManager {
    @Autowired
    // ApplicationContext - lighter than ConfigurableApplicationContext but still
    // has bean creation and thats all i need here
    private ApplicationContext ctx;

    public ClientFacade login(String email, String password, ClientType clientType) {
        // Factory pattern
        switch (clientType) {
            case Administrator:
                AdminFacade adminFacade = ctx.getBean(AdminFacade.class);
                if (adminFacade.login(email, password))
                    return adminFacade;
                break;
            case Company:
                CompanyFacade companyFacade = ctx.getBean(CompanyFacade.class);
                if (companyFacade.login(email, password))
                    return companyFacade;
                break;
            case Customer:
                CustomerFacade customerFacade = ctx.getBean(CustomerFacade.class);
                if (customerFacade.login(email, password))
                    return customerFacade;
                break;
            default:
                break;
        }
        return null;
    }
}