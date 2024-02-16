package site.junyo.minheegame.api.exception;

import lombok.Getter;

@Getter
public class DuplicateUserIdException extends CommonException {

    public DuplicateUserIdException(int code, String msg) {
        super(code, msg);
    }
}
