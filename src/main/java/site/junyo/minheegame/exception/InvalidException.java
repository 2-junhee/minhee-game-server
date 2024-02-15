package site.junyo.minheegame.exception;


import com.sun.jdi.request.InvalidRequestStateException;

public class InvalidException extends InvalidRequestStateException {

    public InvalidException(int code, String msg) {
        super(msg);
    }
}
