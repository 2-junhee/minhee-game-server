package site.junyo.minheegame.api.http.dto.response;

import lombok.Getter;

@Getter
public class CommonResponse {
    private final int code;
    private final String msg;


    public CommonResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
