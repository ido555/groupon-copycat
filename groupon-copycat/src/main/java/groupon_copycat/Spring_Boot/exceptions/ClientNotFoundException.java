package groupon_copycat.Spring_Boot.exceptions;

@SuppressWarnings("serial")
public class ClientNotFoundException extends Exception {
    public ClientNotFoundException() {
        super("This customer / company was not found or you entered incorrect login information");
    }
}
