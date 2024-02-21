package site.junyo.minheegame.user.exception;

import lombok.Getter;
import site.junyo.minheegame.user.util.ResponseInfo;

@Getter
public class InvalidLoginInfoException extends RuntimeException {

    private final int code;
    private final String msg;

    public InvalidLoginInfoException(ResponseInfo info) {
        this.code = info.getCode();
        this.msg = info.getMsg();
    }
}
