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
public class WorkerAndCompanyJwtFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    Logger logger = LoggerFactory.getLogger(WorkerAndCompanyJwtFilter.class);

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
                String companyId = (String) tokenClaims.get("companyId");
                String workerId = (String) tokenClaims.get("workerId");
                if (companyId == null && workerId == null) {
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
        AntPathRequestMatcher deleteApplicationMatcher = new AntPathRequestMatcher("/applications/{id}", "DELETE");
        AntPathRequestMatcher getApplicationByWorkerMatcher = new AntPathRequestMatcher("/applications/findByWorker/{workerId}", "GET");
        AntPathRequestMatcher postCriteriaMatcher = new AntPathRequestMatcher("/criterias/", "POST");
        AntPathRequestMatcher deleteCriteriaMatcher = new AntPathRequestMatcher("/criterias/{id}", "DELETE");
        AntPathRequestMatcher putCriteriaMatcher = new AntPathRequestMatcher("/criterias/{id}", "PUT");
        AntPathRequestMatcher getCriteriaMatcher = new AntPathRequestMatcher("/criterias/{id}", "GET");
        AntPathRequestMatcher acceptApplicationByWorkerMatcher = new AntPathRequestMatcher("/applications/acceptByWorker/{id}", "PUT");

        // accessible que par les companies (c'est le filtre Companies qui s'applique)
        AntPathRequestMatcher postOfferMatcher = new AntPathRequestMatcher("/offers/", "POST");
        AntPathRequestMatcher deleteOfferMatcher = new AntPathRequestMatcher("/offers/{id}", "DELETE");
        AntPathRequestMatcher getAllCompaniesMatcher = new AntPathRequestMatcher("/offers/{id}", "PUT");
        AntPathRequestMatcher getCompanyByIdMatcher = new AntPathRequestMatcher("/applications/findByOfferId/{offerId}");
        AntPathRequestMatcher acceptApplicationByCompanyMatcher = new AntPathRequestMatcher("/applications/acceptByCompany/{id}", "PUT");

        // accessible par tout le monde (public)
        AntPathRequestMatcher getByIdOfferMatcher = new AntPathRequestMatcher("/offers/{id}", "GET");
        AntPathRequestMatcher getAllOfferMatcher = new AntPathRequestMatcher("/offers/", "GET");
        AntPathRequestMatcher getAllOfferByCompanyIdMatcher = new AntPathRequestMatcher("/offers/findByCompanyId/{companyId}", "GET");


        String path = request.getRequestURI();
        // This filter is useless in this MS, we keep it as a reference for other MS
        // We disable it for all routes
        return postApplicationMatcher.matches(request) ||
                deleteApplicationMatcher.matches(request) ||
                getApplicationByWorkerMatcher.matches(request) ||
                postCriteriaMatcher.matches(request) ||
                deleteCriteriaMatcher.matches(request) ||
                putCriteriaMatcher.matches(request) ||
                getCriteriaMatcher.matches(request) ||
                getAllOfferMatcher.matches(request) ||
                getByIdOfferMatcher.matches(request) ||
                getAllOfferByCompanyIdMatcher.matches(request) ||
                postOfferMatcher.matches(request) ||
                deleteOfferMatcher.matches(request) ||
                getAllCompaniesMatcher.matches(request) ||
                getCompanyByIdMatcher.matches(request) ||
                acceptApplicationByCompanyMatcher.matches(request) ||
                acceptApplicationByWorkerMatcher.matches(request);
    }
}