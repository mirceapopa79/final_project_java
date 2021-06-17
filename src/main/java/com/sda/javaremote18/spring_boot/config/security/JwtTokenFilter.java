package com.sda.javaremote18.spring_boot.config.security;

import com.sda.javaremote18.spring_boot.controllers.users.UsersRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.isEmpty;

/**
 * Clasa JwtTokenFilter se ocupa cu identificarea token-ului in request si trimiterea lui spre validare
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UsersRepository userRepo;

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UsersRepository userRepo) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepo = userRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {
        //token example :
        //Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzLG1hcmlhbkBlbWFpbC5jb20iLCJpc3MiOiJjb20uc2RhLmphdmFyZW1vdGUxOC5zcHJpbmdfYm9vdCIsImlhdCI6MTYyMDY2MTg3MSwiZXhwIjoxNjE4OTU4OTAzfQ.GhrkOH0gt_WBmX80eMVmytqH1UAgew4EYGDTKxDKbEF4bSYDsswMzgOv6qWCB_jLDdMHyDkGiaV8Fkspcise5A
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        // Valoare token-ului este separata dupa spatiu " ", apoi se ia a doua pozitie din array-ul obtinut
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            chain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        UserDetails userDetails = userRepo
                .findUserByEmail(jwtTokenUtil.getUsername(token))
                .orElse(null);

        UsernamePasswordAuthenticationToken
                authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                userDetails == null ?
                        List.of() : userDetails.getAuthorities()
        );

        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}