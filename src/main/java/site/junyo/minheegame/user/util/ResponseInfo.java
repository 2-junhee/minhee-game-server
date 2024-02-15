package site.junyo.minheegame.user.util;

import lombok.Getter;

@Getter
public enum ResponseInfo {

    SUCCESS(0, "요청이 성공적으로 처리됐습니다."),
    LOGIN_FAIL(-41, "인증에 실패했습니다. 올바른 사용자 아이디와 비밀번호를 제공해주세요.");

    private final int code;
    private final String msg;

    ResponseInfo(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
