package woosap.Pepple.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import woosap.Pepple.config.auth.OnOAuth2FailureHandler;
import woosap.Pepple.config.auth.OnOAuth2SuccessHandler;
import woosap.Pepple.security.TokenFilter;
import woosap.Pepple.security.TokenServiceImpl;
import woosap.Pepple.service.CustomOAuth2UserService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OnOAuth2SuccessHandler oAuth2SuccessHandler;
    private final OnOAuth2FailureHandler oAuth2FailureHandler;
    private final TokenFilter   tokenFilter;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/",
            "/error",
            "/favicon.ico",
            "/**/*.png",
            "/**/*.gif",
            "/**/*.svg",
            "/**/*.jpg",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic()
                .disable()
            .csrf()
                .disable()
            .formLogin()
                .disable()
            .cors()
            .and()
            .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest)
                    .permitAll()
                .antMatchers("/oauth2/**", "/api/user", "/api/nickname", "/error/**")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                    .and()
            .oauth2Login()
                .authorizationEndpoint()
                    .baseUri("/oauth2/authorize")
                    .and()
                .redirectionEndpoint()
                    .baseUri("/oauth2/callback/*")
                    .and()
                .userInfoEndpoint()
                    .userService(oAuth2UserService)
                    .and()
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(oAuth2FailureHandler)
                .and()
            .addFilterBefore(tokenFilter,
                UsernamePasswordAuthenticationFilter.class);
    }
}
