package com.redacode.redacode.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    /*the UserDetailsService interface is responsible for loading user-specific data for authentication purposes*/
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorisation");
        final String jwt;
        final String userEmail;
        System.out.println("I am heeeere");
        //check if we have the jwt token
        if (authHeader == null || !authHeader.startsWith("Bearer "))
        {
            /*if no we pass the request and response objects to the next filter or endpoint in the Spring Security filter chain*/
            filterChain.doFilter(request, response);
            return ;
        }
        /*ValidateJwtProcess depends on the jwtService*/

        /*extract the token from the AuthorizationHeader from position 7*/
        jwt = authHeader.substring(7);

        /*extract the userEmail using the jwtService*/
        userEmail = jwtService.extractUserEmail(jwt);

        /*check if the user is not null, and it's not authenticated, (the user not connected yet)*/
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){

            /*if so we  need to get the user from te database*/
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            /*check if the user and the token is valid if so we need to update the securityContextHolder*/
            if (jwtService.isTokenValid(jwt, userDetails))
            {
                /*create an object of type UsernamePasswordAuthenticationToken which represents the Authentication Request needed by spring securityContextHolder
                in order to update the securityContext*/
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
