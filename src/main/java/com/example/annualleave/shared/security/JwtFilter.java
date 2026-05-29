package com.example.annualleave.shared.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response
            , FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if(header==null || !header.startsWith("Bearer")){
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);
        Claims claims = jwtProvider.getClaims(token);
        if(claims==null){
            filterChain.doFilter(request, response);
            return;
        }
        Long userId = Long.parseLong(claims.getSubject());
        String role = claims.get("role", String.class);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userId,null
                                                                    , List.of(new SimpleGrantedAuthority("ROLE_" + role)));
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);



    }
}
