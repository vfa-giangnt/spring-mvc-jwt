package gau.nau.springbootjwt.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

@Service
public class JwtService {
    
    public static final String USERNAME = "username";
    public static final String SECRET_KEY = "111111111111111111111111111111111";
    public static final int EXPIRE_TIME = 86400000;
    
    public String generateTokenLogin(String username) {
        
        String token = null;
        try {
            // Create HMAC signer
            JWSSigner signer = new MACSigner(generateShareSecret());
            
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim(USERNAME, username);
            builder.expirationTime(generateExpirationDate());
            
            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            
            // Apply HMAC protection
            signedJWT.sign(signer);
            
            // Serialize to compact form, produces something like
            // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
            token = signedJWT.serialize();
            
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return token;
    }
    
    private JWTClaimsSet getClaimsFromToken(String token) {
        
        JWTClaimsSet claims = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(generateShareSecret());
            if (signedJWT.verify(verifier)) {
                claims = signedJWT.getJWTClaimsSet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }
    
    public String getUsernameFromToken(String token) {
        
        String username = null;
        try {
            JWTClaimsSet claims = getClaimsFromToken(token);
            username = claims.getStringClaim(USERNAME);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        return username;
    }
    
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME);
    }
    
    private Date getExpirationDateFromToken(String token) {
        
        Date expiration = null;
        JWTClaimsSet claims = getClaimsFromToken(token);
        expiration = claims.getExpirationTime();
        
        return expiration;
    }
    
    private byte[] generateShareSecret() {
        
        // Generate 256-bit (32-byte) shared secret
        byte[] sharedSecret = new byte[32];
        sharedSecret = SECRET_KEY.getBytes();
        return sharedSecret;
    }
    
    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    public Boolean validateTokenLogin(String token) {
        
        if (token == null || token.trim().length() == 0) {
            return false;
        }
        
        String username = getUsernameFromToken(token);
        
        if (username == null || username.isEmpty()) {
            return false;
        }
        
        return true;
    }
}
