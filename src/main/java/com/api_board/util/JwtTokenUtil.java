package com.api_board.util;

import com.api_board.exception.ExpiredTokenException;
import com.api_board.exception.InvalidTokenException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private static String SECURITY_KEY = System.getenv("SECRET_KEY_BASE");

    public static String generateAccessToken(Integer data) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000L * 3600 * 24)))
                .setHeaderParam("typ", "JWT")
                .claim("id", data)
                .claim("type", "access_token")
                .signWith(SignatureAlgorithm.HS256, SECURITY_KEY.getBytes())
                .compact();
    }

    public static String generateRefreshToken(Integer data) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000L * 3600 * 24 * 30)))
                .setHeaderParam("typ", "JWT")
                .claim("id", data)
                .claim("type", "refresh_token")
                .signWith(SignatureAlgorithm.HS256, SECURITY_KEY.getBytes())
                .compact();
    }

    public static Integer parseAccessToken(String token) {
        try {
            if(!Jwts.parser().setSigningKey(SECURITY_KEY.getBytes()).parseClaimsJws(token).getBody().get("type").equals("access_token")) throw new InvalidTokenException();
            token = Jwts.parser().setSigningKey(SECURITY_KEY.getBytes()).parseClaimsJws(token).getBody().get("id").toString();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException();
        }
        return Integer.parseInt(token);
    }

    public static Integer parseRefreshToken(String token) {
        try {
            if(!Jwts.parser().setSigningKey(SECURITY_KEY.getBytes()).parseClaimsJws(token).getBody().get("type").equals("refresh_token")) throw new InvalidTokenException();
            token = Jwts.parser().setSigningKey(SECURITY_KEY.getBytes()).parseClaimsJws(token).getBody().get("id").toString();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException();
        }
        return Integer.parseInt(token);
    }
}
