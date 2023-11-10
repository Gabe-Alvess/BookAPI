package be.intecbrussel.bookapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// The AuthorizationFilter is one of the last filters in the chain
// and is responsible for making decisions based on the user's granted authorities.
// AuthorizationFilter checks whether the user has the necessary authorities or roles
// to access the requested resource.
// It uses the information stored in the SecurityContext to determine the user's identity and associated authorities.
// AuthorizationFilter compares the required authorities (configured for the resource) with the granted authorities
// of the current user -> CustomUserDetailsService
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    // Takes instances of JwtUtil and ObjectMapper as parameters, which are injected dependencies.
    // JwtUtil is used for JWT-related operations, and ObjectMapper is used for JSON processing.
    private final JwtUtil jwtUtil;
    private final ObjectMapper mapper;

    public JWTAuthorizationFilter(JwtUtil jwtUtil, ObjectMapper mapper) {
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }

    // Overrides the doFilterInternal method from OncePerRequestFilter.
    // Responsible for processing the JWT and setting up the Spring Security context.
    // Starts by creating an empty map called errorDetails to capture any potential error details.
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {


        Map<String, Object> errorDetails = new HashMap<>();
        // get the JWT token from the HttpServletRequest.
        // If the token is null, it means there is no token present, and it continues to the next filter in the chain.
        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null) {
                filterChain.doFilter(request, response);
                return;
            }

            //  parse the JWT claims. If the claims are not null and are valid
            Claims claims = jwtUtil.resolveClaims(request);
            //  it proceeds to set up Spring Security authentication.
            if (claims != null & jwtUtil.validateClaims(claims)) {
                // Extracts the email from the subject of the claims.
                String email = claims.getSubject();

                List<GrantedAuthority> list = new ArrayList<>();
                String role = claims.get("role", String.class);
                list.add(new CustomAuthority(role));

                // Creates an Authentication object
                // (specifically, a UsernamePasswordAuthenticationToken with an empty password
                // and an empty list of authorities) using the extracted email.
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, "", list);
                //Sets this authentication object in the Spring Security context
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            errorDetails.put("message", "Authentication Error");
            errorDetails.put("details", e.getMessage());
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            mapper.writeValue(response.getWriter(), errorDetails);
            e.printStackTrace();
        }
        // Regardless of success or failure, it calls filterChain.doFilter(request, response)
        // to allow the request to continue down the filter chain.
        filterChain.doFilter(request, response);
    }
}