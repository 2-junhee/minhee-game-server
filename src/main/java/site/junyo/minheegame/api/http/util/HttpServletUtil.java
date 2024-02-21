package site.junyo.minheegame.api.http.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static site.junyo.minheegame.api.http.util.Constant.MINHEE_CHARACTER_ENCODING;
import static site.junyo.minheegame.api.http.util.Constant.MINHEE_CONTENT_TYPE;

@Component
public class HttpServletUtil {

    private final ObjectMapper objectMapper;

    public HttpServletUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Map<String, String> obtainJSONBody(HttpServletRequest request) throws IOException {

        String requestBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        return objectMapper.readValue(requestBody, Map.class);
    }

    public HttpServletResponse setCommonHeaders(HttpServletResponse response) {

        response.setContentType(MINHEE_CONTENT_TYPE);
        response.setCharacterEncoding(MINHEE_CHARACTER_ENCODING);

        return response;
    }

    public HttpServletResponse addBody(HttpServletResponse response, Object dto) throws IOException {

        String body = objectMapper.writeValueAsString(dto);
        response.getWriter().write(body);

        return response;
    }
}
