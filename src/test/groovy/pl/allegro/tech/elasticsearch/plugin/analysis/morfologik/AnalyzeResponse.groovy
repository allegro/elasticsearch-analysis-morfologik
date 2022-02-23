package pl.allegro.tech.elasticsearch.plugin.analysis.morfologik

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

import java.net.http.HttpResponse

class AnalyzeResponse {
    private final static ObjectMapper objectMapper = new ObjectMapper()
    public static final int HTTP_OK = 200

    final int status
    final TokensResult result
    final Map<Object, Object> error

    AnalyzeResponse(HttpResponse<String> response) {
        this.status = response.statusCode()
        this.result = status == HTTP_OK ? objectMapper.readValue(response.body(), TokensResult.class) : null
        this.error = status != HTTP_OK ? objectMapper.readValue(response.body(), Map.class) : null
    }

    @Override
    String toString() {
        return "AnalyzeResult{" +
                "status=" + status +
                ", tokens=" + result +
                ", error=" + error +
                '}'
    }

    static class TokensResult {
        List<Token> tokens

        @Override
        String toString() {
            return tokens
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Token {
        String token

        @Override
        String toString() {
            return token
        }
    }
}
