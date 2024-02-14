package site.junyo.minheegame.api.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserSignUpRequest {

    @NotBlank(message = "ID 입력은 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "유효하지 않은 아이디입니다.")
    private String id;

    @NotBlank(message = "Password 입력은 필수입니다.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$", message = "유효하지 않은 비밀번호입니다.")
    private String password;

    @NotBlank(message = "Nickname 입력은 필수입니다.")
    @Pattern(regexp = "^[a-zA-Z가-힣0-9]{2,20}$", message = "유효하지 않은 닉네임입니다.")
    private String nickname;
}
