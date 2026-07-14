package com.medaccess.Controller;

import com.medaccess.Service.AuthService;
import com.medaccess.dto.Auth.LoginRequestDto;
import com.medaccess.dto.Auth.LoginResponseDto;
import com.medaccess.dto.Auth.SighupResponseDto;
import com.medaccess.dto.Auth.SignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto){
        return authService.login(loginRequestDto);

    }

    @PostMapping("/signup")
    public SighupResponseDto sighUp(@RequestBody SignupRequestDto signupRequestDto){
        return authService.signup(signupRequestDto);
    }


}
