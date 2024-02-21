package site.junyo.minheegame.api.http.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class UserLoginResponse extends CommonResponse {

    private final String nickname;
    private final UUID uuid;

    @Builder
    public UserLoginResponse(int code, String msg, String nickname, UUID uuid) {
        super(code, msg);
        this.nickname = nickname;
        this.uuid = uuid;
    }
}
