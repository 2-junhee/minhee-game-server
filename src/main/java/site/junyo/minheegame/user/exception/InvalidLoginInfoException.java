package site.junyo.minheegame.user.exception;

public class InvalidLoginInfoException extends RuntimeException {

    private final int code;
    private final String msg;

    public InvalidLoginInfoException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
