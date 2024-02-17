package site.junyo.minheegame.jwt;

import java.util.Map;

/**
 * 이 인터페이스는 JWT 발급, 검증, 파싱 기능을 제공하는 인터페이스입니다.
 * JWT 라이브러리를 추상화 하여 쉽게 토큰을 다룰 수 있게 하는 것이 이 인터페이스의 목적입니다.
 *
 * <p>구체 클래스는 스프링 빈으로 등록되어 있으므로 이 인터페이스를 의존 주입 받아서 사용하면 됩니다.
 *
 * <p> Example:
 * <pre>
 * &#064;RestController
 * public class ExamController {
 *
 *     private final JwtService jwtService;
 *
 *     &#064;Autowired
 *     public ExamController(JwtService jwtService) {
 *         this.jwtService = jwtService;
 *     }
 * }
 * </pre>
 * </p>
 *
 * @see JwtServiceImpl
 * @author chocola
 * @since 0.1.0
 */
public interface JwtService {

    /**
     * 토큰 발급 메서드. 매개변수로 payload에 들어갈 항목을 넣어주면 곧바로 토큰을 반환한다.
     * Header, Signature 및 payload 중 iss, iat, exp 값은 내규에 따라 자동으로 생성된다. 즉, 매개변수로 넣어주는 iss, iat, exp 값은 무시된다.
     *
     * <p>
     * 아래 두 값은 반드시 넣어줄 것. 그렇지 않으면 {@code IllegalArgumentException}이 발생한다.
     * <li>sub: user UUID</li>
     * <li>nic: user nickname</li>
     *
     * @param payload 토큰의 payload 부분에 들어갈 데이터 {@code Map}
     * @return 토큰
     * @since 0.1.0
     * @throws IllegalArgumentException {@code payload}에 sub 또는 nic 값이 존재하지 않거나, sub 값이 UUID 형식이 아닌 경우
     */
    String createToken(Map<String, String> payload);

    /**
     * 토큰 검증 메서드. 매개변수로 토큰을 넣어주면 유효한 토큰인지 검증하여 그 결과를 반환한다.
     *
     * <p> 토큰 검증 항목
     * <li>토큰 타입</li>
     * <li>토큰 만료 시간</li>
     * <li>토큰 발급자(iss)</li>
     * <li>토큰 서명</li>
     *
     * @param token 검증할 토큰
     * @return 유효한 토큰이면 true, 그렇지 않으면 false를 반환
     * @since 0.1.0
     */
    boolean isValid(String token);

    /**
     * 토큰의 subject 값 추출 메서드. 매개변수로 토큰을 넣어주면 해당 토큰의 sub 값을 반환한다.
     *
     * <p> 이 메서드는 토큰 서명 검증을 수행한다.
     * 즉, 우리가 발급한 토큰이 아니면 무효한 토큰이라고 판단하고 {@code InvalidTokenException}예외를 발생시킨다.
     *
     * @param token sub 값을 추출할 토큰
     * @return token의 sub 값
     * @since 0.1.0
     * @throws InvalidTokenException 토큰의 서명이 유효하지 않은 경우
     */
    String getSubject(String token);

    /**
     * 토큰의 nickname 값 추출 메서드. 매개변수로 토큰을 넣어주면 해당 토큰의 nic 값을 반환한다.
     *
     * <p> 이 메서드는 토큰 서명 검증을 수행한다.
     * 즉, 우리가 발급한 토큰이 아니면 무효한 토큰이라고 판단하고 {@code InvalidTokenException}예외를 발생시킨다.
     *
     * @param token nic 값을 추출할 토큰
     * @return token의 nic 값
     * @since 0.1.0
     * @throws InvalidTokenException 토큰의 서명이 유효하지 않은 경우
     */
    String getNickname(String token);
}
