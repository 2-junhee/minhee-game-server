package site.junyo.minheegame.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import site.junyo.minheegame.api.http.dto.response.CommonResponse;
import site.junyo.minheegame.api.http.dto.response.UserLoginResponse;
import site.junyo.minheegame.api.http.util.HttpServletUtil;
import site.junyo.minheegame.jwt.JwtService;
import site.junyo.minheegame.user.domain.PrincipalDetails;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static site.junyo.minheegame.api.http.util.Constant.*;
import static site.junyo.minheegame.user.util.ResponseInfo.LOGIN_FAIL;
import static site.junyo.minheegame.user.util.ResponseInfo.SUCCESS;

/**
 * JSON 형식의 로그인 제출을 처리 필터<br>
 * 로그인 양식은
 * Content-Type이 application/json이어야 하며
 * 사용자 이름의 키는 id, 암호의 키는 password로 포함해야 함.
 *
 * <p> Example:
 * <pre>
 * {
 *    "id": "exampleID",
 *    "password": "examplePassword"
 * }
 * </pre>
 * </p>
 * 성공 시, JWT 토큰을 발급해 Header에 Authorization 이름으로 담으며, nickname과 uuid 정보를 body에 담아 200 반환
 * 실패 시, code와 msg를 body에 담고, HttpStatus 401반환
 * <p>
 *
 * @author junhee
 * @since 0.1.0
 */
public class JSONUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String JSON_USERNAME_KEY = "id";
    private static final String JSON_PASSWORD_KEY = "password";
    private static final AntPathRequestMatcher CUSTOM_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/api/v1/login", "POST");

    private final AuthenticationManager authenticationManager;
    private final HttpServletUtil httpServletUtil;
    private final JwtService jwtService;

    public JSONUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, HttpServletUtil httpServletUtil, JwtService jwtService) {
        super(CUSTOM_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        this.authenticationManager = authenticationManager;
        this.httpServletUtil = httpServletUtil;
        this.jwtService = jwtService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {

        if (request.getContentType() == null || !request.getContentType().equals(MINHEE_CONTENT_TYPE)) {
            throw new AuthenticationServiceException("Authentication content type not supported: " + request.getContentType());
        }

        Map<String, String> requestBodyMap = httpServletUtil.obtainJSONBody(request);

        String username = requestBodyMap.get(JSON_USERNAME_KEY);
        String password = requestBodyMap.get(JSON_PASSWORD_KEY);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {

        httpServletUtil.setCommonHeaders(response).setStatus(HttpStatus.OK.value());

        UUID uuid = obtainUUID(authentication);
        String nickname = obtainNickname(authentication);

        Map<String, String> payload = Map.of(PAYLOAD_KEY_SUB, uuid.toString(), PAYLOAD_KEY_NIC, nickname);
        String token = jwtService.createToken(payload);
        response.addHeader(HEADER_NAME_AUTHORIZATION, TOKEN_PREFIX + token);

        UserLoginResponse dto = UserLoginResponse.builder().uuid(uuid).nickname(nickname).code(SUCCESS.getCode()).msg(SUCCESS.getMsg()).build();
        httpServletUtil.addBody(response, dto);
    }

    private UUID obtainUUID(Authentication authentication) {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser().getUuid();
    }

    private String obtainNickname(Authentication authentication) {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUser().getNickname();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {

        httpServletUtil.setCommonHeaders(response).setStatus(HttpStatus.UNAUTHORIZED.value());

        CommonResponse dto = new CommonResponse(LOGIN_FAIL.getCode(), LOGIN_FAIL.getMsg());
        httpServletUtil.addBody(response, dto);
    }

}