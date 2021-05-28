package groupon_copycat.Spring_Boot.web.AOP;

import groupon_copycat.Spring_Boot.beans.ClientWrapper;
import groupon_copycat.Spring_Boot.clientFacades.AdminFacade;
import groupon_copycat.Spring_Boot.clientFacades.ClientFacade;
import groupon_copycat.Spring_Boot.clientFacades.CompanyFacade;
import groupon_copycat.Spring_Boot.clientFacades.CustomerFacade;
import groupon_copycat.Spring_Boot.enums.ClientType;
import groupon_copycat.Spring_Boot.web.controllers.LoginController;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class ControllerAspect {
    private final Map<String, ClientWrapper> sessions = LoginController.sessions;

    @Around("@annotation(groupon_copycat.Spring_Boot.web.AOP.ControllerCheck)")
    public Object doCheck(ProceedingJoinPoint pjp) throws Throwable {
        /* check if client has logged in */
        String token = (String) pjp.getArgs()[0];
        if (!sessions.containsKey(token))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token. Are you logged in?");

        long now = new Date().getTime();
        long lastAction = sessions.get(token).getLastAccess();
        // if 30 minutes passed
        if (now > (lastAction + TimeUnit.MINUTES.toMillis(30)))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Logged out due to 30 minutes of inactivity");
        // refresh last access time
        sessions.get(token).setLastAccess(now);

        /* check if client is trying to use actions outside his role */
        ClientFacade cFac = (sessions.get(token)).getClientFacade();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        // after getting the annotation for this instance of a webController
        // i can get its methods using reflection
        ControllerCheck check = method.getAnnotation(ControllerCheck.class);
        // finally i can find out what the method clientType value is and use it
        ClientType anoType = check.cType();
        // example: if the ClientFacade i got is an AdminFacade
        // it should have @ControllerCheck(cType = ClientType.Administrator)
        // otherwise - forbidden action
        if (cFac instanceof AdminFacade && anoType != ClientType.Administrator
                || cFac instanceof CompanyFacade && anoType != ClientType.Company
                || cFac instanceof CustomerFacade && anoType != ClientType.Customer)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Forbidden Action. Did you try to perform an action outside your role?");

        // continue the original method
        return pjp.proceed();
    }
}
