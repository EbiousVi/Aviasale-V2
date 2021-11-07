package com.ebious.aviasale.service;

import com.ebious.aviasale.domain.apiDto.resp.UserDto;
import com.ebious.aviasale.domain.entity.Role;
import com.ebious.aviasale.domain.entity.Users;
import com.ebious.aviasale.expection.InvalidUserDataException;
import com.ebious.aviasale.repository.RoleRepository;
import com.ebious.aviasale.repository.UserRepository;
import com.ebious.aviasale.domain.apiDto.req.SignUpDto;
import com.ebious.aviasale.domain.enums.Roles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserProfilesService userProfilesService;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
                       UserProfilesService userProfilesService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userProfilesService = userProfilesService;
        this.passwordEncoder = passwordEncoder;
    }

    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by email: " + email));
    }

    public void updateUser(Users user) {
        userRepository.save(user);
    }

    @Transactional
    public void createUser(SignUpDto signUpDto) {
        Users user = new Users();
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setRoles(Collections.singletonList(
                roleRepository.findByRoleName(Roles.ROLE_USER.toString())
                        .orElse(new Role(Roles.ROLE_USER.name())))
        );
        user.setUserProfile(userProfilesService.createUserProfile(signUpDto));
        userRepository.save(user);
    }

    public UserDto getUserDto(Users users, Map<String, String> tokens) {
        UserDto userDto = new UserDto();
        userDto.setEmail(users.getEmail());
        userDto.setFullName(users.getUserProfile().getFullName());
        userDto.setPassengerId(users.getUserProfile().getPassengerId());
        userDto.setPhoneNo(users.getUserProfile().getContactDataJsonb());
        userDto.setAccessToken(tokens.get("accessToken"));
        userDto.setRefreshToken(tokens.get("refreshToken"));
        return userDto;
    }

    @Transactional
    public ResponseEntity<?> signUp(SignUpDto signUpDto) throws InvalidUserDataException {
        Optional<Users> userByEmail = userRepository.findByEmail(signUpDto.getEmail());
        if (userByEmail.isPresent()) {
            return new ResponseEntity<>(
                    "User with email: " + signUpDto.getEmail() + " already exists!",
                    HttpStatus.UNAUTHORIZED
            );
        } else {
            this.createUser(signUpDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
