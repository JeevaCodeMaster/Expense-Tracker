package com.Xtend.Expense_Tracker.Controller;

import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import com.Xtend.Expense_Tracker.DTO.AuthRequest;
import com.Xtend.Expense_Tracker.Entity.RevokedToken;
import com.Xtend.Expense_Tracker.Entity.User;
import com.Xtend.Expense_Tracker.Repository.RevokedTokenRepository;
import com.Xtend.Expense_Tracker.Service.JwtService;
import com.Xtend.Expense_Tracker.Service.MyUDS;
import com.Xtend.Expense_Tracker.Service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
//@CrossOrigin
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	@Autowired
    private  AuthenticationManager authenticationManager;
	@Autowired
    private  JwtService jwtService;
	@Autowired
    private  MyUDS userDetailsService;
	
	@Autowired 
	private UserService userService; 
	
	@Autowired
	private RevokedTokenRepository tokenRepository;
	

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        
        User user = userService.findByUserName(request.getUsername());
         String token = jwtService.generateToken(user);
        

        Map<String, Object> response = new HashMap();
        response.put("token", token);
        response.put("userId", user.getId()); // optional, frontend can directly use it

        return ResponseEntity.ok(response);
        
        
        
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            RevokedToken revoked = new RevokedToken(null,token);
            tokenRepository.save(revoked);
            return ResponseEntity.ok("Logged out successfully!");
        }
        return ResponseEntity.badRequest().body("No token provided!");
    }


}

