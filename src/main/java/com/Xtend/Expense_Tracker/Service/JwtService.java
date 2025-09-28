package com.Xtend.Expense_Tracker.Service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.Xtend.Expense_Tracker.Entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtService {
private static String secretKey="";
	
	
	public JwtService() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keyGen.generateKey();
			 secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static Key getKey() {
		byte[] keyBytes=Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public String generateToken(User userdetails) {
		Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userdetails.getId()); // <-- Add userId claim
        
			
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(userdetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
				
				.signWith(getKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	

	

	
	public static Claims extractAllClaims(String token) {
         return Jwts.parser()
                 .setSigningKey(getKey())
                 .parseClaimsJws(token)
                 .getBody();
         
     }
	 
	  public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
          final Claims claims = extractAllClaims(token);
          return claimsResolver.apply(claims);
      }
	  
	  
	public static String extractUserName(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getSubject);
	}

	public static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
	
	
	public static boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private static boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		 return extractExpiration(token).before(new Date());
	}
	
	
	
	

}
