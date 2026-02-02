// package com.BackBasiFit.controller;

// import com.BackBasiFit.entity.Usuarios;

// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.RestController;


// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//     private final AuthService authService;

//     public AuthController(AuthService authService) {
//         this.authService = authService;
//     }

//     @PostMapping("/registrar")
//     public ResponseEntity<Usuario> registrar(@RequestBody @Valid RegistrarRequest req) {
//         return ResponseEntity.ok(authService.registrar(req));
//     }

//     @PostMapping("/login")
//     public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest req) {
//         return ResponseEntity.ok(authService.login(req));
//     }
// }