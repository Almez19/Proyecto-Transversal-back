package com.BackBasiFit.security;

public class AuthRequest {
    private String email; 
    private String contrasena;

    public AuthRequest() {
        
    }

    public String getEmail() { 
        return email; 
    }

    public void setEmail(String email) { 
        this.email = email; 
    }

    public String getContrasena() { 
        return contrasena; 
    }

    public void setContrasena(String contrasena) { 
        this.contrasena = contrasena; 
    }
}
