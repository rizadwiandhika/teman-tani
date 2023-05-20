package com.temantani.security.service;

import java.security.Key;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  private static final String SECRET_KEY = "404D635166546A576E5A7234753778217A25432A462D4A614E645267556B5870";

  public String extractSubject(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public List<String> extractRoles(String token) {
    return extractClaim(token, (t -> {
      Object rolesClaim = t.getOrDefault("roles", new ArrayList<String>());

      if (rolesClaim instanceof List<?>) {
        List<?> rolesList = (List<?>) rolesClaim;
        if (rolesList.stream().allMatch(o -> o instanceof String)) {
          List<String> roles = rolesList.stream().map(o -> (String) o).collect(Collectors.toList());
          return roles;
        }
      }

      return new ArrayList<String>();
    }));
  }

  public String generateToken(UserDetails userDetails) {
    List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
        .map((s) -> s.substring(5))
        .collect(Collectors.toList());

    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("roles", roles);

    return generateToken(extraClaims, userDetails.getUsername());
  }

  public String generateToken(Map<String, Object> extraClaims, String sub) {
    Date now = Date.from(Instant.now());
    Date expire = Date.from(Instant.now().plusSeconds(3600));

    return Jwts
        .builder()
        .setClaims(extraClaims)
        .setSubject(sub)
        .setIssuedAt(now)
        .setExpiration(expire)
        .signWith(getSignKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    String sub = extractSubject(token);
    return (sub.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    Date expireDate = extractClaim(token, Claims::getExpiration);
    return Date.from(Instant.now()).after(expireDate);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
    Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
  }

  private Key getSignKey() {
    // The secret key must be minimum 256 bits length as required from
    // Keys.hmacShaKeyFor
    byte[] decoded = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(decoded);
  }

}
