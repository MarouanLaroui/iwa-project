package fr.polytech.ig5.mnm.recruitmentms.filters;

import fr.polytech.ig5.mnm.recruitmentms.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CompanyJwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    Logger logger = LoggerFactory.getLogger(CompanyJwtFilter.class);

    @Override
    public void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        final String requestTokenHeader = request.getHeader("Authorization");

        // JWT Token is in the form "Bearer token". Remove Bearer word and get
        // only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String token = requestTokenHeader.substring(7);
            try {
                Claims tokenClaims = this.jwtUtils.decodeJWT(token);
                String id = (String) tokenClaims.get("companyId");
                if (id == null) {
                    response.reset();
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                } else {
                    request.getRequestDispatcher(request.getServletPath()).forward(request, response);
                }
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to decode JWT");
            } catch (ExpiredJwtException e) {
                logger.warn("JWT has expired");
                response.reset();
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            logger.warn("No JWT in the request");
            response.reset();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {
        // accessible que part les workers (c'est le filtre Worker qui s'applique)
        AntPathRequestMatcher findWorksByWorkerIdMatcher = new AntPathRequestMatcher("/works/findByWorkerId/{id}", "GET");

        // accessible par les deux (c'est le filtre WorkerAndCompany qui s'applique)
        AntPathRequestMatcher getWorkByIdMatcher = new AntPathRequestMatcher("/works/{id}", "GET");
        AntPathRequestMatcher putWorkByIdMatcher = new AntPathRequestMatcher("/works/{id}", "PUT");
        AntPathRequestMatcher deleteWorkByIdMatcher = new AntPathRequestMatcher("/works/{id}", "DELETE");

        // accessible par tout le monde (public)

        String path = request.getRequestURI();
        return findWorksByWorkerIdMatcher.matches(request) ||
                getWorkByIdMatcher.matches(request) ||
                putWorkByIdMatcher.matches(request) ||
                deleteWorkByIdMatcher.matches(request);
    }
}