package groupon_copycat.Spring_Boot.web.AOP;

import groupon_copycat.Spring_Boot.enums.ClientType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerCheck {
    ClientType cType();
}
