package groupon_copycat.Spring_Boot.exceptions;

@SuppressWarnings("serial")
public class NameException extends Exception {

    public NameException() {
        super("company name cannot be changed and must be unique");
    }
}
