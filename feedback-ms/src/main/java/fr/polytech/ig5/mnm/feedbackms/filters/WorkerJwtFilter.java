package fr.polytech.ig5.mnm.feedbackms.filters;

import fr.polytech.ig5.mnm.feedbackms.utils.JwtUtils;
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
public class WorkerJwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    Logger logger = LoggerFactory.getLogger(WorkerJwtFilter.class);

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
                String id = (String) tokenClaims.get("workerId");
                if (id == null) {
                    response.reset();
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                } else {
                    request.getRequestDispatcher(request.getServletPath()).forward(request, response);
                }
                // chain.doFilter(request, response);
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
        AntPathRequestMatcher postFeedBackMatcher = new AntPathRequestMatcher("/feedbacks/", "POST");
        AntPathRequestMatcher getFeedbacksBySenderMatcher = new AntPathRequestMatcher("/feedbacks/bySenderId/{id}", "GET");
        AntPathRequestMatcher getFeedbacksByReceiverMatcher = new AntPathRequestMatcher("/feedbacks/byReceiverId/{id}", "GET");
        AntPathRequestMatcher getStatsMatcher = new AntPathRequestMatcher("/feedbacks/stats/{id}", "GET");
        AntPathRequestMatcher deleteFeedbackMatcher = new AntPathRequestMatcher("/feedbacks/{id}", "DELETE");

        String path = request.getRequestURI();
        return postFeedBackMatcher.matches(request) ||
                getStatsMatcher.matches(request) ||
                getFeedbacksBySenderMatcher.matches(request) ||
                getFeedbacksByReceiverMatcher.matches(request) ||
                deleteFeedbackMatcher.matches(request);
    }
}