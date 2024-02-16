package site.junyo.minheegame.api.exception;


import lombok.Getter;

import java.util.List;

@Getter
public class CommonException extends RuntimeException {

    private final int code;

    public CommonException(int code, String errors) {
        super(errors);
        this.code = code;
    }
}
