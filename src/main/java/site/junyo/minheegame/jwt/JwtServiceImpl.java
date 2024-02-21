package site.junyo.minheegame.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
class JwtServiceImpl implements JwtService {

    private static final long TOKEN_VALIDITY_TIME = Long.parseLong(System.getenv("TOKEN_VALIDITY_TIME")) * 1000;
    private static final String HEADER_TYPE = "JWS";
    private static final String PAYLOAD_ISSUER = "2junhei";

    private final SecretKey secretKey;

    public JwtServiceImpl() {
        String secretKey = System.getenv("SECRET_KEY");
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    @Override
    public String createToken(Map<String, String> payload) {
        validPayload(payload);

        Date iat = new Date();
        Date exp = new Date(iat.getTime() + TOKEN_VALIDITY_TIME);

        return Jwts.builder()
                .header()
                .type(HEADER_TYPE)
                .and()

                .claims()
                .add(payload)
                .issuer(PAYLOAD_ISSUER)
                .issuedAt(iat)
                .expiration(exp)
                .and()

                .signWith(secretKey)
                .compact();
    }

    private void validPayload(Map<String, String> payload) {
        String sub = payload.get("sub");
        String nic = payload.get("nic");

        if (Objects.isNull(sub) || Objects.isNull(nic)) {
            throw new IllegalArgumentException("토큰 발급 오류! sub 또는 nic 값을 넣어주어야 합니다.");
        }

        try {
            UUID.fromString(sub);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("토큰 발급 오류! sub 값이 UUID 형식이 아닙니다.");
        }
    }

    @Override
    public boolean isValid(String token) {
        JwtParser jwtParser = getJwtParser();

        Jws<Claims> jws;
        try {
            jws = jwtParser.parseSignedClaims(token);
        } catch (Exception e) {
            return false;
        }

        boolean typeEq = jws.getHeader().getType().equals(HEADER_TYPE);
        if (!typeEq) {
            return false;
        }

        Claims payload = jws.getPayload();
        boolean issEq = payload.getIssuer().equals(PAYLOAD_ISSUER);
        Date expiration = payload.getExpiration();
        boolean passedExp = expiration.before(new Date());
        if (!issEq || passedExp) {
            return false;
        }

        return true;
    }

    @Override
    public String getSubject(String token) {
        JwtParser jwtParser = getJwtParser();
        Jws<Claims> jws = getParsedJws(token, jwtParser);
        return jws.getPayload().getSubject();
    }

    @Override
    public String getNickname(String token) {
        JwtParser jwtParser = getJwtParser();
        Jws<Claims> jws = getParsedJws(token, jwtParser);
        return jws.getPayload().get("nic", String.class);
    }

    private JwtParser getJwtParser() {
        return Jwts.parser()
                .verifyWith(secretKey)
                .requireIssuer(PAYLOAD_ISSUER)
                .build();
    }

    private Jws<Claims> getParsedJws(String token, JwtParser jwtParser) {
        try {
            return jwtParser.parseSignedClaims(token);
        } catch (Exception e) {
            throw new InvalidTokenException("토큰의 서명이 유효하지 않습니다.");
        }
    }
}
