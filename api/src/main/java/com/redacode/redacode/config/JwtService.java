package com.redacode.redacode.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final  String SIGNING_KEY = "7A244326452948404D635166546A576E5A7234753778214125442A472D4A614E";

    public String extractUserEmail(String token) {
        //using a method reference to the "getSubject" method of the "Claims" class
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractAllClaims(String token){
        return (Claims) Jwts.parserBuilder().setSigningKey(getSignIngKey()).build().parse(token).getBody();
    }

    private Key getSignIngKey() {
        //decode the signing key
        byte[] keyBytes = Decoders.BASE64.decode(SIGNING_KEY);
        //Keys.hmacShaKeyFor is one of the signing algorithms
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //extractClaim is a generic method that takes the token and a claimResolver function of type claims and returns T
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    //generate a token just from the user details without extra claims
    public String generateToken(UserDetails userDetails)
    {
        return generateToken(new HashMap<>(), userDetails);
    }
    //generateToken takes a Map of string and object that will contain the claims we want to add
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails)
    {
        //setIssuedAt means when the claims are created this will hel pu to calculate the expiration date and to check the validation of the token
        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *48)).
                signWith(getSignIngKey(), SignatureAlgorithm.HS256).
                compact();
    }

    //validate the token if it belongs to the user
    public boolean isTokenValid(String token, UserDetails userDetails)
    {
        final String userName = extractUserEmail(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


}
