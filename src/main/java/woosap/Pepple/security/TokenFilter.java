package woosap.Pepple.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;
import woosap.Pepple.service.CustomUserDetailService;

@Slf4j
@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    private CustomUserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String accessToken = parseToken(request);
        if (StringUtils.hasText(accessToken) && tokenService.validToken(accessToken)) {
            UserDetails details = userDetailService
                .loadUserByUsername(tokenService.getSubject(accessToken));
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                details, null,
                details.getAuthorities());
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    private String parseToken(HttpServletRequest request) {
        String authInfo = tokenService.resolveToken(request);

        if (StringUtils.hasText(authInfo) && authInfo.startsWith("Bearer ")) {
            return authInfo.substring(7);
        }
        return null;
    }
}