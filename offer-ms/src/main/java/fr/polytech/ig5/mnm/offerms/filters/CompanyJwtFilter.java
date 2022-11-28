package fr.polytech.ig5.mnm.offerms.filters;

import fr.polytech.ig5.mnm.offerms.utils.JwtUtils;
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
        AntPathRequestMatcher postApplicationMatcher = new AntPathRequestMatcher("/offers/{offerId}/applications/", "POST");
        AntPathRequestMatcher getRecommendationsMatcher = new AntPathRequestMatcher("/offers/recommendations/", "GET");
        AntPathRequestMatcher deleteApplicationMatcher = new AntPathRequestMatcher("/applications/{id}", "DELETE");
        AntPathRequestMatcher getApplicationByWorkerMatcher = new AntPathRequestMatcher("/applications/", "GET");
        AntPathRequestMatcher postCriteriaMatcher = new AntPathRequestMatcher("/criterias/", "POST");
        AntPathRequestMatcher putCriteriaMatcher = new AntPathRequestMatcher("/criterias/", "PUT");
        AntPathRequestMatcher deleteCriteriaMatcher = new AntPathRequestMatcher("/criterias/", "DELETE");
        AntPathRequestMatcher getCriteriaMatcher = new AntPathRequestMatcher("/criterias/", "GET");
        AntPathRequestMatcher acceptApplicationByIdMatcher = new AntPathRequestMatcher("/applications/acceptByWorker/{id}", "PUT");

        // accessible par les deux (c'est le filtre WorkerAndCompany qui s'applique)
        AntPathRequestMatcher getApplicationByIdMatcher = new AntPathRequestMatcher("/applications/{id}", "GET");

        // accessible par tout le monde (public)
        AntPathRequestMatcher getByIdOfferMatcher = new AntPathRequestMatcher("/offers/{id}", "GET");
        AntPathRequestMatcher getAllOfferMatcher = new AntPathRequestMatcher("/offers/", "GET");
        AntPathRequestMatcher getAllOfferByCompanyIdMatcher = new AntPathRequestMatcher("/offers/findByCompanyId/{companyId}", "GET");

        String path = request.getRequestURI();
        return postApplicationMatcher.matches(request) ||
                deleteApplicationMatcher.matches(request) ||
                getRecommendationsMatcher.matches(request) ||
                getApplicationByWorkerMatcher.matches(request) ||
                postCriteriaMatcher.matches(request) ||
                putCriteriaMatcher.matches(request) ||
                deleteCriteriaMatcher.matches(request) ||
                getCriteriaMatcher.matches(request) ||
                getAllOfferMatcher.matches(request) ||
                getByIdOfferMatcher.matches(request) ||
                getAllOfferByCompanyIdMatcher.matches(request) ||
                getApplicationByIdMatcher.matches(request) ||
                acceptApplicationByIdMatcher.matches(request);
    }
}