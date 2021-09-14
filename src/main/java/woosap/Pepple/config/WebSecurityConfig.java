package woosap.Pepple.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.util.UriComponentsBuilder;
import org.yaml.snakeyaml.util.UriEncoder;
import woosap.Pepple.config.auth.OnOAuth2FailureHandler;
import woosap.Pepple.dto.oauth2.GithubUserInfo;
import woosap.Pepple.dto.oauth2.Oauth2Info;
import woosap.Pepple.security.TokenFilter;
import woosap.Pepple.security.TokenService;
import woosap.Pepple.security.TokenServiceImpl;
import woosap.Pepple.service.CustomOAuth2UserService;
import woosap.Pepple.util.Constants;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService oAuth2UserService;
    private final OnOAuth2FailureHandler oAuth2FailureHandler;
    private final TokenFilter   tokenFilter;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers(
                "/",
                "/error",
                "/favicon.ico",
                "/**/*.png",
                "/**/*.gif",
                "/**/*.svg",
                "/**/*.jpg",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**"
            );
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
                .antMatchers("/oauth2/**", "/user", "/nickname", "/error/**", "/room")
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
                .successHandler(new OnOAuth2SuccessHandler())
                .failureHandler(oAuth2FailureHandler)
                .and()
            .addFilterBefore(tokenFilter,
                UsernamePasswordAuthenticationFilter.class);
    }

    @Slf4j
    private static class OnOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

            String detailRedirectUrl = Constants.REDIRECT_URL + "/register";
            String redirectUrlWithParams;

            log.info("Success");
            if (response.isCommitted()) {
                log.debug("이미 처리된 응답입니다.");
                return ;
            }

            Oauth2Info oAuth2User = (Oauth2Info) authentication.getPrincipal();

            redirectUrlWithParams = UriComponentsBuilder.fromUriString(detailRedirectUrl)
                .queryParam("id", UriEncoder.encode(oAuth2User.getId()))
                .queryParam("image", UriEncoder.encode(oAuth2User.getImagePath()))
                .build()
                .toUriString();
            getRedirectStrategy().sendRedirect(request, response, redirectUrlWithParams);
        }
    }
}
