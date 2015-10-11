package classwork.authorization.exceptionPackage;

/**
 * Created by Булат on 11.10.2015.
 */
public class authorizationException extends RuntimeException {
    public authorizationException() {
    }

    public authorizationException(final String message) {
        super(message);
    }
}