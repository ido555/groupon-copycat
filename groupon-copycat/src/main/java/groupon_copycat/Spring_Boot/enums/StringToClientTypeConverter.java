package groupon_copycat.Spring_Boot.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// this converter is automatically used by spring when it tries to convert a String into a ClientType enum.
// made this just to see how it works but also to accept values regardless of capitalization
@Component
public class StringToClientTypeConverter implements Converter<String, ClientType> {
    @Override
    public ClientType convert(String source) {
        if (source.equalsIgnoreCase("Administrator"))
            return ClientType.Administrator;
        if (source.equalsIgnoreCase("Company"))
            return ClientType.Company;
        if (source.equalsIgnoreCase("Customer"))
            return ClientType.Customer;
        return null;
    }
}