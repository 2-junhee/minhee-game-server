package site.junyo.minheegame.api.http.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidResponse {

    private final int code;
    private final String msg;
    private final List<String> msgs;

    public InvalidResponse(int code, String msg, List<String> msgs) {
        this.code = code;
        this.msg = msg;
        this.msgs = msgs;
    }
}
