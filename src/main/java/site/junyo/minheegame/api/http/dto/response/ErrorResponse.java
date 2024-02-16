package site.junyo.minheegame.api.http.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse extends CommonResponse{
    private List<String> errors;

    public ErrorResponse(int code, String msg, List<String> errors) {
        super(code, msg);
        this.errors = errors;
    }
}
