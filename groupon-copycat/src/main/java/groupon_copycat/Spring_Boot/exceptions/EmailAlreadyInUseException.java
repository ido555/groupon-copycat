package groupon_copycat.Spring_Boot.exceptions;

@SuppressWarnings("serial")
public class EmailAlreadyInUseException extends Exception {
    public EmailAlreadyInUseException() {
        super("this email is already in use. try a different one");
    }
}
