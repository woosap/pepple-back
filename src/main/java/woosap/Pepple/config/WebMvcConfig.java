package woosap.Pepple.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    // 1 hours for preflight request cache
    private final Long MAX_PREFLIGHT_RETENTION_SECS = 3600L;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
            .addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(MAX_PREFLIGHT_RETENTION_SECS);
    }
}
