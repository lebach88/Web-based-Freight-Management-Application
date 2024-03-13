package com.dev.backendspringboot.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService{
    @Override
    public Token generateToken(UserDetails userDetails) {
        Token token = new Token();
        token.setToken(Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstant.JWT_EXPIRATION))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact());
        return token;
    }
    private Key getSignKey(){
        byte[] key = Decoders.BASE64.decode(JwtConstant.JWT_KEY);
        return Keys.hmacShaKeyFor(key);
    }
    @Override
    public Token generateRefreshToken(Map<String, Objects> extraClaims, UserDetails userDetails) {
        Token token = new Token();
        token.setToken(Jwts.builder().setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstant.JWT_EXPIRATION))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact());
        return token;
    }

    private Claims extraToken(String token){
        try {
            return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
        } catch (SignatureException e) {
// DONE:           chữ ký của JWT không hợp lệ.
            throw new SignatureException(e.getMessage());
        }catch (DecodingException e) {
// DONE:           JWT có khoảng cách
            throw new DecodingException(e.getMessage());
        }catch (MalformedJwtException e) {
// DONE:           JWT không thể được phân tích cú pháp
            throw new MalformedJwtException(e.getMessage());
        } catch (ExpiredJwtException e) {
// DONE:          JWT đã hết hạn.
            throw new ExpiredJwtException(e.getHeader(),null,e.getMessage());
        } catch (IllegalArgumentException e) {
// DONE:           chuỗi yêu cầu của JWT rỗng
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    private <T> T extraClaim(String token, Function<Claims,T> ClaimsResolve){
        final Claims claims = extraToken(token);
        return ClaimsResolve.apply(claims);
    }
    @Override
    public String extraUsername(String token) {
        return extraClaim(token,Claims::getSubject);
    }

    @Override
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = extraUsername(token);
        return (username.equals(userDetails.getUsername()) & !isTokenExpiration(token));
    }

    private boolean isTokenExpiration(String token) {
        return extraClaim(token,Claims::getExpiration).before(new Date());
    }
}
