package org.example.demo.commons.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.demo.dao.entity.UserInfoEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Joword
 * @date: 2023/8/8 14:40
 * @version: 0.1.0
 * @description: generation utils
 * jwt token format: header|payload|signature
 * header:{"alg":"HS512", "typ":"JWT"}
 * payload:{"sub":"test","created":12313123212,"exp":123123156}
 * signature:hmacsha512(BASE64UrlEncode(header)+"."+base64UrlEncode(payload), secret)
 */
public class JwtTokenUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * to get a outdate time for jwt token with no argument
     *
     * @return Date
     */
    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * to get a outdate time for jwt token with argument
     *
     * @param token String
     *
     * @return DATE
     */
    private Date getExpirationDateToken(String token) {
        Claims claims = getClaimsToken(token);
        return claims.getExpiration();
    }

    /**
     * According to claims, to generate the jwt token
     *
     * @param claims kv
     *
     * @return String
     */
    private String generate(Map<String, Object> claims) {
        return Jwts.builder().setClaims(claims).setExpiration(getExpirationDate()).signWith(SignatureAlgorithm.ES512, secret).compact();
    }

    /**
     * to obtain the body from the claims jwt token
     *
     * @param token String
     *
     * @return Claims
     */
    private Claims getClaimsToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            logger.error("JWT validation failed: {}", token);
        }
        return claims;
    }

    /**
     * is outdated
     *
     * @param token String
     *
     * @return boolean
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpirationDateToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * to get the username
     *
     * @param token String
     *
     * @return String
     */
    public String getUserName(String token) {
        String username;
        try {
            Claims claims = getClaimsToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            logger.error("username exception: {}", token);
            username = null;
        }
        return username;
    }

    /**
     * to validate the token
     *
     * @param token          String
     * @param userInfoEntity user information entity
     *
     * @return boolean
     */
    public boolean validate(String token, UserInfoEntity userInfoEntity) {
        String username = getUserName(token);
        return username.equals(userInfoEntity.getUserId()) && isTokenExpired(token);
    }

    /**
     * to make a jwt token by UserInfoEntity
     *
     * @param userInfoEntity Entity
     *
     * @return String
     */
    public String generateUserInfoToken(UserInfoEntity userInfoEntity) {
        Map<String, Object> claims = new ConcurrentHashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userInfoEntity.getUserId());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generate(claims);
    }

    public String freshToken(String token) {
        Claims claims = getClaimsToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generate(claims);
    }

    public boolean refresh(String token) {
        return !isTokenExpired(token);
    }


}
