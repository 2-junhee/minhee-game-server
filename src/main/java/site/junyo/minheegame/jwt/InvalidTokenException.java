package site.junyo.minheegame.jwt;

/**
 * 토큰이 유효하지 않은 경우 발생하는 예외
 *
 * @since 0.1.0
 */
public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }
}
