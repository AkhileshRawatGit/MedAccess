package com.medaccess.Service;

import com.medaccess.Repository.UserRepo;
import com.medaccess.Security.AuthUtil;
import com.medaccess.dto.Auth.LoginRequestDto;
import com.medaccess.dto.Auth.LoginResponseDto;
import com.medaccess.dto.Auth.SighupResponseDto;
import com.medaccess.dto.Auth.SignupRequestDto;
import com.medaccess.entity.User;
import com.medaccess.entity.UserType;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AuthService {

    private final AuthenticationManager manager;
    private final AuthUtil authUtil;
    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        User user = (User) authentication.getPrincipal();
        String token = authUtil.generateAccessToken(user);
        return new LoginResponseDto(token, user.getId());
    }

    public SighupResponseDto signup(SignupRequestDto signupRequestDto) {
        User user = userRepo.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if (user != null)
            throw new RuntimeException("user alredy exist");

        user = userRepo.save(User.builder()
                .username(signupRequestDto.getUsername())
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .phone(signupRequestDto.getPhone())
                .userType(UserType.Customer)
                .build());
        return mapper.map(user, SighupResponseDto.class);
    }
}
