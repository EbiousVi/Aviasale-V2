package com.ebious.aviasale.controller.auth;

import com.ebious.aviasale.domain.apiDto.req.LogoutDto;
import com.ebious.aviasale.domain.apiDto.req.RefreshTokenDto;
import com.ebious.aviasale.domain.apiDto.req.SignInDto;
import com.ebious.aviasale.domain.apiDto.req.SignUpDto;
import com.ebious.aviasale.domain.entity.Users;
import com.ebious.aviasale.expection.InvalidUserDataException;
import com.ebious.aviasale.security.JwtTokenProvider;
import com.ebious.aviasale.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.security.Principal;

@RestController
@Validated
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/api/auth/sign-in")
    public ResponseEntity<?> signIn(@RequestBody @NotNull SignInDto signInDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInDto.getEmail(), signInDto.getPassword())
            );
            Users userByEmail = userService.getUserByEmail(signInDto.getEmail());
            return new ResponseEntity<>(
                    userService.getUserDto(userByEmail, jwtTokenProvider.getTokens(userByEmail)), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody @NotNull RefreshTokenDto refreshTokenDto, Principal principal) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        boolean isValid = jwtTokenProvider.validateToken(refreshToken);
        if (isValid) {
            Users users = userService.getUserByEmail(jwtTokenProvider.getEmailFromToken(refreshToken));
            if (users.getRefreshToken().equals(refreshToken)) {
                return new ResponseEntity<>(jwtTokenProvider.getTokens(users), HttpStatus.OK);
            }
        }
        logger.warn("Refresh Token no valid!" + principal.getName());
        return new ResponseEntity<>("Refresh Token no valid!", HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/api/auth/logout")
    public void logout(@RequestBody @NotNull LogoutDto logoutDto,
                       HttpServletRequest request, HttpServletResponse response) {
        Users userByEmail = userService.getUserByEmail(logoutDto.getEmail());
        userByEmail.setRefreshToken(null);
        userService.updateUser(userByEmail);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response, null);
    }

    @PostMapping("/api/auth/sign-up")
    public ResponseEntity<?> signUp(@RequestBody @NotNull SignUpDto signUpDto) throws InvalidUserDataException {
        return userService.signUp(signUpDto);
    }
}
