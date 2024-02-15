package site.junyo.minheegame.api.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import site.junyo.minheegame.user.domain.User;

@Getter @Setter
@ToString
public class UserLoginRequest {

    @NotBlank(message = "ID 입력은 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "유효하지 않은 아이디입니다.")
    private String id;

    @NotBlank(message = "Password 입력은 필수입니다.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!]).{8,20}$", message = "유효하지 않은 비밀번호입니다.")
    private String password;

    public User toEntity() {
        return User.builder()
                .id(id)
                .password(password)
                .build();
    }
}
