package com.instagram.Configuration;

import com.instagram.Exceptions.ExpiredJwtException;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtUtil {

  private String SECRET_KEY = "secret";
  private long JWT_EXPIRY = 8 * 60 * 60; //8 HOURS
  private long JWT_REFRESH_EXPIRY = 7 * 24 * 60 * 60;//7 days

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
  }

  public String getPasswordFromToken(String token) {
    return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().get("password").toString();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(String username, String password) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("password", password);
    return doGenerateToken(claims, username);
  }

 /* public String generateRefreshToken(DefaultClaims claims) {
    Map<String, Object> map = new HashMap<String, Object>();
    for (Map.Entry<String, Object> entry : claims.entrySet()) {
      map.put(entry.getKey(), entry.getValue());
    }
    return doGenerateRefreshToken(map, map.get("sub").toString());
  }
  */

  public String generateRefreshToken(String username, String password) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("password", password);
    return doGenerateRefreshToken(claims, username);
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {

    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis()+ JWT_EXPIRY * 1000))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
  }

  private String doGenerateRefreshToken(Map<String, Object> claims, String subject) {

    return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + JWT_REFRESH_EXPIRY * 1000))
        .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) throws ExpiredJwtException {
      String username = getUsernameFromToken(token);
      if(isTokenExpired(token)){
        throw new ExpiredJwtException(getAllClaimsFromToken(token), "Token Expired");
      }
      return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

  }

}
