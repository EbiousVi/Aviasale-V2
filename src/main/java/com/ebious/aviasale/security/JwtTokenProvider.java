package com.ebious.aviasale.security;

import com.ebious.aviasale.domain.entity.Role;
import com.ebious.aviasale.domain.entity.Users;
import com.ebious.aviasale.expection.JwtAuthenticationException;
import com.ebious.aviasale.service.UserService;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    private final String signature = "Victor";
    private final long accessTokenLifetime = 60_000;
    private final long refreshTokenLifetime = 86_400_000;


    @Autowired
    public JwtTokenProvider(UserDetailsServiceImpl userDetailsService,
                            @Lazy UserService userService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    public String generateAccessToken(String email, Collection<Role> role) {
        return generateToken(email, role, accessTokenLifetime, false);
    }

    public String generateRefreshToken(String email, Collection<Role> role) {
        return generateToken(email, role, refreshTokenLifetime, true);
    }

    private String generateToken(String email, Collection<Role> role, long validityInMilliseconds, boolean isRefresh) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("refresh", isRefresh);
        claims.put("roles", role.stream().collect(Collectors.toMap(Role::getRoleId, Role::getRoleName)));
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            throw new JwtAuthenticationException("Access token is expired", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            logger.warn("UnsupportedJwtException", e);
            throw new JwtAuthenticationException("You have a big trouble, dude!", HttpStatus.UNAUTHORIZED);
        } catch (MalformedJwtException e) {
            logger.warn("MalformedJwtException", e);
            throw new JwtAuthenticationException("You have a big trouble, dude!", HttpStatus.UNAUTHORIZED);
        } catch (SignatureException e) {
            logger.error("SignatureException", e);
            throw new JwtAuthenticationException("Try to re login or contact to customer support!", HttpStatus.UNAUTHORIZED);
        }
    }

    public String getEmailFromToken(String token) {
        return Jwts.parser().setSigningKey(signature).parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getEmailFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Map<String, String> getTokens(Users users) {
        String accessToken = this.generateAccessToken(users.getEmail(), users.getRoles());
        String refreshToken = this.generateRefreshToken(users.getEmail(), users.getRoles());
        users.setRefreshToken(refreshToken);
        userService.updateUser(users);
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }
}
