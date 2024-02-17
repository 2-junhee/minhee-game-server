package site.junyo.minheegame.api.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class UserLoginRequest {

    @NotBlank(message = "ID 입력은 필수입니다.")
    private String id;

    @NotBlank(message = "Password 입력은 필수입니다.")
    private String password;

}
