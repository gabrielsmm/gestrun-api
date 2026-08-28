package com.gabrielsmm.gestrun.config;

import com.gabrielsmm.gestrun.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.DefaultCorsProcessor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class SecurityConfigCorsTest {

    @Test
    void devePermitirPreflightPatchParaOrigemConfigurada() throws Exception {
        SecurityConfig securityConfig = new SecurityConfig(mock(JwtAuthenticationFilter.class));
        ReflectionTestUtils.setField(securityConfig, "allowedOrigins", java.util.List.of("http://localhost:4200"));
        MockHttpServletRequest request = new MockHttpServletRequest("OPTIONS", "/api/corridas/1/publicacao");
        request.addHeader("Origin", "http://localhost:4200");
        request.addHeader("Access-Control-Request-Method", "PATCH");
        MockHttpServletResponse response = new MockHttpServletResponse();

        CorsConfiguration cors = securityConfig.corsConfigurationSource().getCorsConfiguration(request);

        assertThat(cors.checkOrigin(request.getHeader("Origin"))).isEqualTo("http://localhost:4200");
        assertThat(cors.checkHttpMethod(HttpMethod.PATCH)).isNotNull();
        assertThat(new DefaultCorsProcessor().processRequest(cors, request, response)).isTrue();
        assertThat(response.getHeader("Access-Control-Allow-Methods")).contains("PATCH");
    }
}
