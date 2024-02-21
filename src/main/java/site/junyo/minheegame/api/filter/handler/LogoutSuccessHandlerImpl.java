package site.junyo.minheegame.api.filter.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import site.junyo.minheegame.api.http.dto.response.CommonResponse;
import site.junyo.minheegame.api.http.util.HttpServletUtil;

import java.io.IOException;

import static site.junyo.minheegame.user.util.ResponseInfo.SUCCESS;

public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    private final HttpServletUtil httpServletUtil;

    public LogoutSuccessHandlerImpl(HttpServletUtil httpServletUtil) {
        this.httpServletUtil = httpServletUtil;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        httpServletUtil.setCommonHeaders(response).setStatus(HttpStatus.OK.value());

        CommonResponse dto = new CommonResponse(SUCCESS.getCode(), SUCCESS.getMsg());
        httpServletUtil.addBody(response, dto);
    }
}
