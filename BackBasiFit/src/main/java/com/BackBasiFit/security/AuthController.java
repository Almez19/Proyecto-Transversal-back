package com.BackBasiFit.security;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.BackBasiFit.security.AuthRequest;
import com.BackBasiFit.security.AuthResponse;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasena())
    );

        UserDetails user = (UserDetails) auth.getPrincipal();

        String roles = user.getAuthorities().toString();
        String token = jwtService.generateToken(user, Map.of("roles", roles));

        return ResponseEntity.ok(new AuthResponse(token));
    }
}

