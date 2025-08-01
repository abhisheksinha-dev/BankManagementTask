package com.bankMnagaement.BankManagementTask.security;

import com.bankMnagaement.BankManagementTask.entities.AppUser;
import com.bankMnagaement.BankManagementTask.repositories.AppUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Service
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtSecurityTokenUtils jwtSecurityTokenUtils;

    AppUserRepository userRepo;

    public JwtSecurityFilter(JwtSecurityTokenUtils jwtSecurityTokenUtils, AppUserRepository userRepo) {
        this.jwtSecurityTokenUtils = jwtSecurityTokenUtils;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        /**
         * by this line we will get whole Authorization header in format like -
         * "Bearer(SPACE)TOKEN"
         */
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null){
            String username = jwtSecurityTokenUtils.extractUsername(authHeader);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                AppUser user = userRepo.findByUsername(username).orElseThrow();

                if (user != null && jwtSecurityTokenUtils.validateToken(authHeader,user.getUsername())){
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(user.getUsername(),null,
                                    List.of(new SimpleGrantedAuthority(user.getRole().name())));

                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    logger.info("authenticated user "+ username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request,response);
    }
}
