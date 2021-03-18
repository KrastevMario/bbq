package bbqRemake.exceptions;

public class InvalidCustomerException extends Throwable {
    @Override
    public String getMessage() {
        return "The choosen customer is invalid.";
    }
}
